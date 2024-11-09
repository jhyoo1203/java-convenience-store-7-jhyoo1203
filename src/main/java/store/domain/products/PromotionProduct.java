package store.domain.products;

import store.domain.events.Promotion;
import store.domain.events.Promotions;

public final class PromotionProduct extends BaseProduct {

    private static final int OUT_OF_STOCK_QUANTITY = 0;


    private final Promotion promotion;

    public PromotionProduct(String name, int price, int stockQuantity, String promotionName) {
        super(name, price, stockQuantity);
        this.promotion = Promotions.findByName(promotionName);
    }

    public String getPromotionName() {
        return promotion.getName();
    }

    @Override
    public void purchase(int quantity) {
        if (isOutOfStock(quantity)) {
            stockQuantity = OUT_OF_STOCK_QUANTITY;
            return;
        }
        this.stockQuantity -= quantity;
    }
}
