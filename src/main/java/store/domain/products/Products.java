package store.domain.products;

import store.domain.events.Promotion;
import store.domain.order.OrderRequest;
import store.global.exception.ConvenienceStoreException;
import store.global.exception.ErrorMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Products {

    private static final List<BaseProduct> products = new ArrayList<>();
    private static final int INT_ZERO = 0;

    public static int calculateTotalGiftCount(String productName, int quantity) {
        BaseProduct product = findByName(productName);
        if (product instanceof PromotionProduct promotionProduct) {
            return promotionProduct.calculateGiftCount(quantity);
        }
        return INT_ZERO;
    }

    public static boolean canAddPromotionGift(String productName, int quantity) {
        BaseProduct product = findByName(productName);
        if (!(product instanceof PromotionProduct promotionProduct)) {
            return false;
        }
        if (quantity >= promotionProduct.stockQuantity) {
            return false;
        }
        Promotion promotion = promotionProduct.getPromotion();
        return quantity % (promotion.getBuyCount() + 1) == promotion.getBuyCount();
    }

    public static int getNonPromotionalCount(String productName, int quantity) {
        BaseProduct product = findByName(productName);
        if (product instanceof PromotionProduct promotionProduct) {
            return calculateNonPromotionalQuantity(promotionProduct, quantity);
        }
        return INT_ZERO;
    }

    private static int calculateNonPromotionalQuantity(PromotionProduct product, int quantity) {
        if (quantity > product.stockQuantity) {
            int nonPromotionQuantity = product.getNonPromotionQuantity();
            int normalProductQuantity = quantity - product.stockQuantity;
            return nonPromotionQuantity + normalProductQuantity;
        }
        return INT_ZERO;
    }

    public static void purchaseProducts(String input) {
        OrderRequest orderRequest = OrderRequest.from(input);
        orderRequest.getItems().forEach(item ->
                purchase(item.getProductName(), item.getQuantity()));
    }

    public static void purchase(String productName, int quantity) {
        BaseProduct product = findByName(productName);
        if (product instanceof PromotionProduct) {
            purchasePromotionProductFirst((PromotionProduct) product, quantity);
            return;
        }
        product.purchase(quantity);
    }

    private static void purchasePromotionProductFirst(PromotionProduct product, int quantity) {
        int promotionPurchaseQuantity = Math.min(quantity, product.stockQuantity);
        product.purchase(promotionPurchaseQuantity);

        if (quantity > promotionPurchaseQuantity) {
            findNormalProductByName(product.getName())
                    .purchase(quantity - promotionPurchaseQuantity);
        }
    }

    public static int calculateTotalGiftAmount(OrderRequest orderRequest) {
        return orderRequest.getItems().stream()
                .mapToInt(item -> {
                    BaseProduct product = findByName(item.getProductName());
                    int giftCount = calculateTotalGiftCount(item.getProductName(), item.getQuantity());
                    return giftCount * product.getPrice();
                })
                .sum();
    }

    public static List<BaseProduct> findAll() {
        return Collections.unmodifiableList(products);
    }

    public static BaseProduct findByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> ConvenienceStoreException.from(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    public static Product findNormalProductByName(String name) {
        return (Product) products.reversed()
                .stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> ConvenienceStoreException.from(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    public static void addProduct(BaseProduct product) {
        products.add(product);
    }
}
