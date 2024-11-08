package store.domain.events;

public class MembershipSale {

    private static final int PERCENT_NUMBER = 100;
    private static final double SALE_RATE = 30.0;
    private static final int MAX_SALE_AMOUNT = 8000;
    private static final int NO_DISCOUNT = 0;

    private final double saleRate;
    private final int maxSaleAmount;

    public MembershipSale() {
        this.saleRate = SALE_RATE;
        this.maxSaleAmount = MAX_SALE_AMOUNT;
    }

    public int discount(final int withoutPromotionAmount) {
        if (isDiscountable(withoutPromotionAmount)) {
            return Math.min(calculateDiscountAmount(withoutPromotionAmount), maxSaleAmount);
        }
        return NO_DISCOUNT;
    }

    private boolean isDiscountable(final int amount) {
        return calculateDiscountAmount(amount) < MAX_SALE_AMOUNT;
    }

    private int calculateDiscountAmount(final int amount) {
        return (int) (amount * (saleRate / PERCENT_NUMBER));
    }

}
