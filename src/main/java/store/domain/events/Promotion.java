package store.domain.events;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int buyCount;
    private final int giftCount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(String name, int buyCount, int giftCount, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buyCount = buyCount;
        this.giftCount = giftCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion from(String name, int buyCount, int giftCount, LocalDate startDate, LocalDate endDate) {
        return new Promotion(name, buyCount, giftCount, startDate, endDate);
    }

    public boolean isValidDate(LocalDate currentDate) {
        return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
    }

    public String getName() {
        return name;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public int getGiftCount() {
        return giftCount;
    }
}
