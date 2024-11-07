package store.domain.products;

public final class Product extends ProductProperty {

    public Product(String name, int price, int stockQuantity) {
        super(name, price, stockQuantity);
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
