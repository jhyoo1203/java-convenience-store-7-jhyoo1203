package store.view;

import store.domain.dto.ProductDto;
import store.domain.products.Products;

import java.util.List;

import static store.view.constants.OutputMessage.*;

public class OutputView {

    private static final int OUT_OF_STOCK_QUANTITY = 0;

    public void displayProducts() {
        System.out.println(RETENTION_STATUS_MESSAGE);
        List<ProductDto> products = getProductDtos();
        products.forEach(OutputView::displayProduct);
    }

    private List<ProductDto> getProductDtos() {
        return Products.findAll()
                .stream()
                .map(ProductDto::from)
                .toList();
    }

    private static void displayProduct(ProductDto product) {
        System.out.printf(IN_STOCK_PRODUCT_FORMAT,
                product.name(),
                product.price(),
                getStockDisplay(product.stockQuantity()));
    }

    private static String getStockDisplay(int stockQuantity) {
        if (stockQuantity == OUT_OF_STOCK_QUANTITY) {
            return OUT_OF_STOCK_PRODUCT;
        }
        return stockQuantity + STOCK_QUANTITY_SUFFIX;
    }

    public void displayOrderDetailsPrefix() {
        System.out.println(ORDER_DETAILS_PREFIX);
    }

    public void displayOrderDetails(String productName, int quantity, int total) {
        System.out.printf(ORDER_DETAILS_FORMAT, productName, quantity, total);
    }

    public void displayGiftDetailsPrefix() {
        System.out.println(GIFT_DETAILS_PREFIX);
    }

    public void displayGiftDetails(String productName, int quantity) {
        System.out.printf(GIFT_DETAILS_FORMAT, productName, quantity);
    }

    public void displayPaymentDetailsPrefix() {
        System.out.println(PAYMENT_DETAILS_PREFIX);
    }

    public void displayTotalAmount(int quantity, int total) {
        System.out.printf(TOTAL_AMOUNT_FORMAT, quantity, total);
    }

    public void displayEventDiscount(int amount) {
        System.out.printf(EVENT_DISCOUNT_FORMAT, amount);
    }

    public void displayMembershipDiscount(int amount) {
        System.out.printf(MEMBERSHIP_DISCOUNT_FORMAT, amount);
    }

    public void displayMoneyToPay(int amount) {
        System.out.printf(MONEY_TO_PAY_FORMAT, amount);
    }
}
