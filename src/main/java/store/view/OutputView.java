package store.view;

import store.domain.dto.ProductDto;
import store.domain.products.Products;

import java.util.List;

public class OutputView {

    private static final String RETENTION_STATUS_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    private static final String PROMOTION_PRODUCT_FORMAT = "- %s %,d원 %d개 %s%n";
    private static final String IN_STOCK_PRODUCT_FORMAT = "- %s %,d원 %d개%n";
    private static final String OUT_OF_STOCK_PRODUCT = "재고 없음";

    public void displayProducts() {
        System.out.println(RETENTION_STATUS_MESSAGE);
        List<ProductDto> products = getProductDtos();
        if (products.isEmpty()) {
            displayOutOfStockProduct();
        }
        products.forEach(OutputView::displayProduct);
    }

    private List<ProductDto> getProductDtos() {
        return Products.findAll()
                .stream()
                .map(ProductDto::from)
                .toList();
    }

    private void displayOutOfStockProduct() {
        System.out.printf(OUT_OF_STOCK_PRODUCT);
    }

    private static void displayProduct(ProductDto product) {
        if (product.hasPromotion()) {
            displayPromotionProduct(product);
            return;
        }
        displayNormalProduct(product);
    }

    private static void displayPromotionProduct(ProductDto product) {
        System.out.printf(PROMOTION_PRODUCT_FORMAT,
                product.name(),
                product.price(),
                product.stockQuantity(),
                product.promotionName());
    }

    private static void displayNormalProduct(ProductDto product) {
        System.out.printf(IN_STOCK_PRODUCT_FORMAT,
                product.name(),
                product.price(),
                product.stockQuantity());
    }
}
