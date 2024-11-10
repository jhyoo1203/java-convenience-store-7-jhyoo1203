package store.controller;

import store.domain.events.MembershipSale;
import store.domain.order.OrderItem;
import store.domain.order.OrderRequest;
import store.domain.products.*;
import store.global.exception.ConvenienceStoreException;
import store.view.InputView;
import store.view.OutputView;

import java.util.function.Supplier;

public class ConvenientStoreController {

    private final DataInitializer dataInitializer;
    private final OutputView outputView;
    private final InputView inputView;
    private final MembershipSale membershipSale;

    public ConvenientStoreController() {
        this.dataInitializer = new DataInitializer();
        this.outputView = new OutputView();
        this.inputView = new InputView();
        this.membershipSale = new MembershipSale();
    }

    public void run() {
        initialize();
        processOrder();
    }

    private void initialize() {
        dataInitializer.initialize();
        outputView.displayProducts();
    }

    private void processOrder() {
        String orderQuery = retryOnException(inputView::readPurchaseProduct);
        OrderRequest orderRequest = OrderRequest.from(orderQuery);
        processGiftItems(orderRequest);
        processOrderDetails(orderRequest);
        completeOrder(orderQuery);
    }

    private void processOrderDetails(OrderRequest orderRequest) {
        int totalQuantity = orderRequest.calculateTotalQuantity();
        int totalAmount = orderRequest.calculateTotalAmount();
        int eventDiscountAmount = Products.calculateTotalGiftAmount(orderRequest);
        int membershipDiscountAmount = processMembership(orderRequest);
        displayOrderDetails(orderRequest);
        displayGiftsDetails();
        displayPaymentDetails(totalQuantity, totalAmount, eventDiscountAmount, membershipDiscountAmount);
    }

    private void displayOrderDetails(OrderRequest orderRequest) {
        outputView.displayOrderDetailsPrefix();
        for (OrderItem orderItem : orderRequest.getItems()) {
            outputView.displayOrderDetails(orderItem.getProductName(), orderItem.getQuantity(), orderItem.getTotal());
        }
    }

    private void displayGiftsDetails() {
        if (hasNoGifts()) {
            return;
        }
        if (hasNoValidGifts()) {
            return;
        }
        displayValidGifts();
    }

    private boolean hasNoGifts() {
        return GiftProducts.getGiftEntries().isEmpty();
    }

    private boolean hasNoValidGifts() {
        return GiftProducts.getGiftEntries().stream()
                .noneMatch(gift -> gift.getValue() > 0);
    }

    private void displayValidGifts() {
        outputView.displayGiftDetailsPrefix();
        GiftProducts.getGiftEntries().stream()
                .filter(gift -> gift.getValue() > 0)
                .forEach(gift -> outputView.displayGiftDetails(gift.getKey(), gift.getValue()));
    }

    private void displayPaymentDetails(int totalQuantity, int totalAmount, int eventDiscountAmount, int membershipDiscountAmount) {
        outputView.displayPaymentDetailsPrefix();
        outputView.displayTotalAmount(totalQuantity, totalAmount);
        outputView.displayEventDiscount(eventDiscountAmount);
        outputView.displayMembershipDiscount(membershipDiscountAmount);
        outputView.displayMoneyToPay(totalAmount - eventDiscountAmount - membershipDiscountAmount);
    }

    private void processGiftItems(OrderRequest orderRequest) {
        for (OrderItem orderItem : orderRequest.getItems()) {
            handleGiftItem(orderItem, orderRequest);
        }
    }

    private void handleGiftItem(OrderItem orderItem, OrderRequest orderRequest) {
        String productName = orderItem.getProductName();
        int quantity = orderItem.getQuantity();
        int nonPromotionalCount = Products.getNonPromotionalCount(productName, quantity);
        int giftCount = calculateGiftCount(productName, quantity);
        if (nonPromotionalCount > 0) {
            processIncludeNonPromotion(productName, nonPromotionalCount, giftCount);
            return;
        }
        if (Products.canAddPromotionGift(productName, quantity)) {
            processGiftAddition(productName, giftCount, orderRequest);
            return;
        }
        GiftProducts.addGiftProducts(productName, giftCount);
    }

    private void processIncludeNonPromotion(String productName, int nonPromotionalCount, int giftCount) {
        boolean isGiftAdd = retryOnException(() -> inputView.readNonePromotionSalePurchase(productName, nonPromotionalCount));
        if (isGiftAdd) {
            GiftProducts.addGiftProducts(productName, giftCount);
        }
    }

    private int calculateGiftCount(String productName, int quantity) {
        return Products.calculateTotalGiftCount(
                productName,
                quantity
        );
    }

    private void processGiftAddition(String productName, int giftCount, OrderRequest orderRequest) {
        boolean isGiftAdd = retryOnException(() -> inputView.readAdditionalPromotionProducts(productName));
        if (isGiftAdd) {
            GiftProducts.addGiftProducts(productName, giftCount+1);
            Products.purchase(productName, 1);
            OrderItem orderItem = orderRequest.findByName(productName);
            orderItem.addOrderQuantity();
        }
    }

    private int processMembership(OrderRequest orderRequest) {
        boolean isMembership = retryOnException(inputView::readMembershipSalePurchase);
        int discountAmount = 0;
        if (isMembership) {
            discountAmount = getDiscountAmount(orderRequest, discountAmount);
        }
        return discountAmount;
    }

    private int getDiscountAmount(OrderRequest orderRequest, int discountAmount) {
        return orderRequest.getItems().stream()
                .map(item -> Products.findByName(item.getProductName()))
                .filter(product -> !(product instanceof PromotionProduct))
                .mapToInt(product -> membershipSale.discount(
                        product.calculateTotalAmount(
                                orderRequest.findByName(product.getName()).getQuantity()
                        )
                ))
                .sum() + discountAmount;
    }

    private void completeOrder(String orderQuery) {
        Products.purchaseProducts(orderQuery);
        GiftProducts.clear();
        boolean isContinue = retryOnException(inputView::readPurchaseContinue);
        System.out.println();
        if (isContinue) {
            run();
        }
    }

    private <T> T retryOnException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (ConvenienceStoreException e) {
            System.out.println(e.getMessage());
            return retryOnException(supplier);
        }
    }
}
