package store.domain.order;

public record OrderItem(
        String productName,
        int quantity
) {
}
