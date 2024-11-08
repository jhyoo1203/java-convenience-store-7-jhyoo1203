package store.domain.dto;

import store.domain.products.BaseProduct;
import store.domain.products.PromotionProduct;

public record ProductDto(
        String name,
        int price,
        int stockQuantity,
        String promotionName
) {
    public static ProductDto from(BaseProduct product) {
        if (product instanceof PromotionProduct promotionProduct) {
            return new ProductDto(
                    promotionProduct.getName(),
                    promotionProduct.getPrice(),
                    promotionProduct.getStockQuantity(),
                    promotionProduct.getPromotionName()
            );
        }
        return new ProductDto(
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                null
        );
    }

    public boolean hasPromotion() {
        return promotionName != null;
    }
}
