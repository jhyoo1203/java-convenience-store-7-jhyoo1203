package store.domain.products;

public final class PromotionProduct extends ProductProperty {

    private final PromotionType promotionType;

    public PromotionProduct(String name, int price, int stockQuantity, String promotionName) {
        super(name, price, stockQuantity);
        this.promotionType = PromotionType.from(promotionName);
    }

    @Override
    public void purchase(int quantity) {
        decreaseStock(quantity);
    }

    @Override
    public boolean canPurchase(int quantity) {
        return stockQuantity >= quantity;
    }
}
