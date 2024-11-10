package store.domain.events;

public class MembershipSale {

    private static final int PERCENT_NUMBER = 100;
    private static final double SALE_RATE = 30.0;
    private static final int MAX_SALE_AMOUNT = 8000;

    private final double saleRate;
    private final int maxSaleAmount;

    public MembershipSale() {
        this.saleRate = SALE_RATE;
        this.maxSaleAmount = MAX_SALE_AMOUNT;
    }

    public int discount(final int withoutPromotionAmount) {
        return Math.min(calculateDiscountAmount(withoutPromotionAmount), maxSaleAmount);
    }

    private int calculateDiscountAmount(final int amount) {
        return (int) (amount * (saleRate / PERCENT_NUMBER));
    }

}
