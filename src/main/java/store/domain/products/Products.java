package store.domain.products;

import store.domain.order.OrderRequest;
import store.global.exception.ConvenienceStoreException;
import store.global.exception.ErrorMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Products {

    private static final List<BaseProduct> products = new ArrayList<>();
    private static final int NO_GIFT = 0;

    public static int calculateTotalGiftCount(String productName, int quantity) {
        BaseProduct product = findByName(productName);
        if (product instanceof PromotionProduct promotionProduct) {
            return promotionProduct.calculateGiftCount(quantity);
        }
        return NO_GIFT;
    }

    public static void purchaseProducts(String input) {
        OrderRequest orderRequest = OrderRequest.from(input);
        orderRequest.getItems().forEach(item ->
                purchase(item.productName(), item.quantity()));
    }

    private static void purchase(String productName, int quantity) {
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
