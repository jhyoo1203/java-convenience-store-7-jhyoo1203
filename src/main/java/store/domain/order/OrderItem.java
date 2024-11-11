package store.domain.order;

import store.domain.products.BaseProduct;
import store.domain.products.Products;

public class OrderItem {

    private static final int INCREASE_VALUE = 1;

    private final String productName;
    private int quantity;

    public OrderItem(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public void addOrderQuantity() {
        quantity += INCREASE_VALUE;
    }

    public int getTotal() {
        BaseProduct product = Products.findByName(productName);
        return product.getPrice() * quantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}
