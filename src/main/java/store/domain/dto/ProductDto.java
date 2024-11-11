package store.domain.dto;

import store.domain.products.BaseProduct;
import store.domain.products.Products;
import store.domain.products.PromotionProduct;

public record ProductDto(
        String name,
        int price,
        int stockQuantity,
        String promotionName,
        boolean needsOutOfStockVersion
) {
    public static ProductDto from(BaseProduct product) {
        if (product instanceof PromotionProduct promotionProduct) {
            boolean hasNormalVersion = Products.findAll().stream()
                    .anyMatch(p -> p.getName().equals(product.getName()) && !(p instanceof PromotionProduct));

            return new ProductDto(
                    promotionProduct.getName(),
                    promotionProduct.getPrice(),
                    promotionProduct.getStockQuantity(),
                    promotionProduct.getPromotionName(),
                    !hasNormalVersion
            );
        }

        return new ProductDto(
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                null,
                false
        );
    }

    public boolean hasPromotion() {
        return promotionName != null;
    }
}
