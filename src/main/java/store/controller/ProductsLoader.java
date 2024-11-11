package store.controller;

import store.domain.products.BaseProduct;
import store.domain.products.Product;
import store.domain.products.PromotionProduct;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ProductsLoader {

    private static final Path RESOURCE_PATH = Path.of("src", "main", "resources");
    private static final String PRODUCTS_FILENAME = "products.md";
    private static final int HEADER_INDEX = 1;
    private static final String DELIMITER = ",";
    private static final String NO_PROMOTION = "null";

    public List<BaseProduct> loadProducts() {
        Path productsPath = RESOURCE_PATH.resolve(PRODUCTS_FILENAME);

        try (Stream<String> lines = Files.lines(productsPath)) {
            return parseProducts(lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<BaseProduct> parseProducts(Stream<String> lines) {
        return lines.skip(HEADER_INDEX)
                .map(line -> Arrays.asList(line.split(DELIMITER)))
                .map(this::createProduct)
                .toList();
    }

    private BaseProduct createProduct(List<String> values) {
        String name = values.getFirst();
        int price = Integer.parseInt(values.get(1));
        int stockQuantity = Integer.parseInt(values.get(2));
        String promotionName = values.get(3);
        if (NO_PROMOTION.equals(promotionName)) {
            return new Product(name, price, stockQuantity);
        }
        return new PromotionProduct(name, price, stockQuantity, promotionName);
    }
}
