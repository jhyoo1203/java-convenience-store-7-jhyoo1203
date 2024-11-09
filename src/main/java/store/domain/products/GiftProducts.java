package store.domain.products;

import java.util.HashMap;
import java.util.Map;

public class GiftProducts {

    private static final Map<String, Integer> giftProducts = new HashMap<>();

    public static void addGiftProducts(String productName, int quantity, int price) {
        giftProducts.put(productName, quantity);
    }
}
