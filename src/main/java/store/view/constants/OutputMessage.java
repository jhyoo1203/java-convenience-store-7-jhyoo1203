package store.view.constants;

public class OutputMessage {

    public static final String RETENTION_STATUS_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    public static final String PROMOTION_PRODUCT_FORMAT = "- %s %,d원 %s %s%n";
    public static final String IN_STOCK_PRODUCT_FORMAT = "- %s %,d원 %s%n";
    public static final String OUT_OF_STOCK_PRODUCT = "재고 없음";
    public static final String ORDER_DETAILS_PREFIX = "\n==============W 편의점================\n상품명\t\t\t\t수량\t\t\t금액";
    public static final String ORDER_DETAILS_FORMAT = "%-15s\t%d%,16d\n";
    public static final String GIFT_DETAILS_PREFIX = "=============증\t\t정===============";
    public static final String GIFT_DETAILS_FORMAT = "%-15s\t%d\n";
    public static final String PAYMENT_DETAILS_PREFIX = "==============================";
    public static final String TOTAL_AMOUNT_FORMAT = "총구매액\t\t\t\t%d\t\t\t%,d\n";
    public static final String EVENT_DISCOUNT_FORMAT = "행사할인\t\t\t\t\t\t\t-%,d\n";
    public static final String MEMBERSHIP_DISCOUNT_FORMAT = "멤버십할인\t\t\t\t\t\t\t-%,d\n";
    public static final String MONEY_TO_PAY_FORMAT = "내실돈\t\t\t\t\t\t\t%,d\n\n";
    public static final String STOCK_QUANTITY_SUFFIX = "개";
}
