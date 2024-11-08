package store.domain.products;

import store.domain.events.Promotion;
import store.domain.events.Promotions;

public final class PromotionProduct extends BaseProduct {

    private final Promotion promotion;

    public PromotionProduct(String name, int price, int stockQuantity, String promotionName) {
        super(name, price, stockQuantity);
        this.promotion = Promotions.findByName(promotionName);
    }
}
