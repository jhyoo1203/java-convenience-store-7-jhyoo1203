package store.domain.products;

import store.global.exception.ConvenienceStoreException;
import store.global.exception.ErrorMessage;

public sealed class BaseProduct permits Product, PromotionProduct {

    protected final String name;
    protected final int price;
    protected int stockQuantity;

    public BaseProduct(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void purchase(int quantity) {
        decreaseStock(quantity);
    }

    private void decreaseStock(int quantity) {
        if (!canPurchase(quantity)) {
            throw ConvenienceStoreException.from(ErrorMessage.OUT_OF_STOCK);
        }
        this.stockQuantity -= quantity;
    }

    private boolean canPurchase(int quantity) {
        return stockQuantity >= quantity;
    }

    public String getName() {
        return name;
    }

    private int getStockQuantity() {
        return stockQuantity;
    }
}
