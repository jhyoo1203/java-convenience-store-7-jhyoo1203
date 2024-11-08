package store.domain.dto;

public record PurchaseProductDto(
        String productName,
        int quantity,
        int total
) {
}
