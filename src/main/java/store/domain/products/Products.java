package store.domain.products;

import store.global.exception.ConvenienceStoreException;
import store.global.exception.ErrorMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Products {

    private static final List<BaseProduct> products = new ArrayList<>();
    private static final String ORDER_PRODUCT_REGEX = "[\\[\\]]";
    private static final String BLANK = "";
    private static final String PRODUCT_DELIMITER = ",";
    private static final String EACH_PRODUCT_DELIMITER = "-";
    private static final int PRODUCT_NAME_INDEX = 0;
    private static final int QUANTITY_INDEX = 1;

    public static void purchaseProducts(String input) {
        Arrays.stream(input.replaceAll(ORDER_PRODUCT_REGEX, BLANK).split(PRODUCT_DELIMITER))
                .map(str -> str.split(EACH_PRODUCT_DELIMITER))
                .forEach(parts -> purchase(
                        parts[PRODUCT_NAME_INDEX],
                        Integer.parseInt(parts[QUANTITY_INDEX])
                ));
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

        if (!product.isOutOfStock(quantity)) {
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
