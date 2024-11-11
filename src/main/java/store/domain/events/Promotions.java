package store.domain.events;

import store.global.exception.ConvenienceStoreException;
import store.global.exception.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

public class Promotions {

    private static final List<Promotion> promotions = new ArrayList<>();

    public static Promotion findByName(String name) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> ConvenienceStoreException.from(ErrorMessage.PROMOTION_NOT_FOUND));
    }

    public static void addPromotion(Promotion promotion) {
        promotions.add(promotion);
    }
}
