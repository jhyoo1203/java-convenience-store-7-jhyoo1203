package store.domain.products;

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
        this.stockQuantity -= quantity;
    }

    public boolean isOutOfStock(int quantity) {
        return stockQuantity < quantity;
    }

    public int calculateTotalAmount(int quantity) {
        return price * quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}
