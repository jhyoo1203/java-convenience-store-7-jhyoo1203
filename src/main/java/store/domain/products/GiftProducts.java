package store.domain.products;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class GiftProducts {

    private static final Map<String, Integer> giftProducts = new HashMap<>();

    public static void addGiftProducts(String productName, int quantity) {
        giftProducts.put(productName, quantity);
    }

    public static Set<Entry<String, Integer>> getGiftEntries() {
        return giftProducts.entrySet();
    }
}
