package store.domain.products;

import store.domain.events.Promotion;
import store.domain.events.Promotions;

public final class PromotionProduct extends BaseProduct {

    private static final int INT_ZERO = 0;

    private final Promotion promotion;

    public PromotionProduct(String name, int price, int stockQuantity, String promotionName) {
        super(name, price, stockQuantity);
        this.promotion = Promotions.findByName(promotionName);
    }

    public String getPromotionName() {
        return promotion.getName();
    }

    public int calculateGiftCount(int quantity) {
        if (!canAddPromotionGift(quantity)) {
            return INT_ZERO;
        }
        int numberOfSets = quantity / promotion.getBuyCount();
        return numberOfSets * promotion.getGiftCount();
    }

    @Override
    public void purchase(int quantity) {
        if (isOutOfStock(quantity)) {
            stockQuantity = INT_ZERO;
            return;
        }
        this.stockQuantity -= quantity;
    }

    public boolean canAddPromotionGift(int quantity) {
        return quantity % promotion.getBuyCount() == INT_ZERO;
    }

}
