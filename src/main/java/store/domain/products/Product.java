package store.domain.products;

public final class Product extends BaseProduct {

    public Product(String name, int price, int stockQuantity) {
        super(name, price, stockQuantity);
    }

    @Override
    public void purchase(int quantity) {
        if (isOutOfStock(quantity)) {
            return;
        }
        this.stockQuantity -= quantity;
    }
}
