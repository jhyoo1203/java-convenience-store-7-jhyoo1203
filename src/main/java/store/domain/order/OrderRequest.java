package store.domain.order;

import store.global.exception.ConvenienceStoreException;
import store.global.exception.ErrorMessage;

import java.util.Arrays;
import java.util.List;

public class OrderRequest {

    private final List<OrderItem> items;

    private static final String ORDER_PRODUCT_REGEX = "[\\[\\]]";
    private static final String BLANK = "";
    private static final String PRODUCT_DELIMITER = ",";
    private static final String EACH_PRODUCT_DELIMITER = "-";
    private static final int PRODUCT_NAME_INDEX = 0;
    private static final int QUANTITY_INDEX = 1;

    private OrderRequest(List<OrderItem> items) {
        this.items = items;
    }

    public static OrderRequest from(String input) {
        List<OrderItem> items = Arrays.stream(input.replaceAll(ORDER_PRODUCT_REGEX, BLANK).split(PRODUCT_DELIMITER))
                .map(String::trim)
                .map(OrderRequest::parseOrderItem)
                .toList();

        return new OrderRequest(items);
    }

    private static OrderItem parseOrderItem(String orderItemStr) {
        String[] parts = orderItemStr.split(EACH_PRODUCT_DELIMITER);
        return new OrderItem(
                parts[PRODUCT_NAME_INDEX],
                Integer.parseInt(parts[QUANTITY_INDEX])
        );
    }

    public OrderItem findByName(String productName) {
        return items.stream()
                .filter(item -> item.getProductName().equals(productName))
                .findFirst()
                .orElseThrow(() -> ConvenienceStoreException.from(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    public int calculateTotalQuantity() {
        return items.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
    }

    public int calculateTotalAmount() {
        return items.stream()
                .mapToInt(OrderItem::getTotal)
                .sum();
    }

    public List<OrderItem> getItems() {
        return items;
    }
}
