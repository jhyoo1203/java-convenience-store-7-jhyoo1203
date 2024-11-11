package store.domain.products;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.events.Promotion;
import store.domain.events.Promotions;

import java.time.LocalDate;

public final class PromotionProduct extends BaseProduct {

    private static final int INT_ZERO = 0;

    private final Promotion promotion;

    public PromotionProduct(String name, int price, int stockQuantity, String promotionName) {
        super(name, price, stockQuantity);
        this.promotion = Promotions.findByName(promotionName);
    }

    public int calculateGiftCount(int quantity) {
        if (!promotion.isValidDate(LocalDate.from(DateTimes.now()))) {
            return INT_ZERO;
        }
        int availableQuantity = Math.min(quantity, stockQuantity);
        int numberOfSets = availableQuantity / (promotion.getBuyCount() + 1);
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

    public int getNonPromotionQuantity() {
        return stockQuantity % (promotion.getBuyCount() + 1);
    }

    public String getPromotionName() {
        return promotion.getName();
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
