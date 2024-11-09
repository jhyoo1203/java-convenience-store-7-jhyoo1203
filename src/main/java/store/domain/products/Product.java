package store.domain.products;

import store.global.exception.ConvenienceStoreException;
import store.global.exception.ErrorMessage;

public final class Product extends BaseProduct {

    public Product(String name, int price, int stockQuantity) {
        super(name, price, stockQuantity);
    }

    @Override
    public void purchase(int quantity) {
        if (isOutOfStock(quantity)) {
            throw ConvenienceStoreException.from(ErrorMessage.OUT_OF_STOCK);
        }
        this.stockQuantity -= quantity;
    }
}
