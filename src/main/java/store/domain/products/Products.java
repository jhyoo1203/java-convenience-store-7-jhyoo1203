package store.domain.products;

import store.global.exception.ConvenienceStoreException;
import store.global.exception.ErrorMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Products {

    private static final List<BaseProduct> products = new ArrayList<>();

    public static void purchase(String productName, int quantity) {
        BaseProduct product = findByName(productName);
        product.purchase(quantity);
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

    public static void addProduct(BaseProduct product) {
        products.add(product);
    }
}
