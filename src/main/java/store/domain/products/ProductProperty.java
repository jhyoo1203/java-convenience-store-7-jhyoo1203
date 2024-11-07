package store.domain.products;

import store.global.exception.ConvenienceStoreException;
import store.global.exception.ErrorMessage;

public sealed abstract class ProductProperty permits Product, PromotionProduct, EventProduct {

    protected final String name;
    protected final int price;
    protected int stockQuantity;

    public ProductProperty(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public abstract void purchase(int quantity);
    public abstract boolean canPurchase(int quantity);

    protected void decreaseStock(int quantity) {
        if (!canPurchase(quantity)) {
            throw ConvenienceStoreException.from(ErrorMessage.OUT_OF_STOCK);
        }
        this.stockQuantity -= quantity;
    }

    protected String getName() {
        return name;
    }

    protected int getStockQuantity() {
        return stockQuantity;
    }
}
