package store.domain.products;

import store.global.exception.ConvenienceStoreException;
import store.global.exception.ErrorMessage;

import java.util.Arrays;

public enum PromotionType {

    MD_PICK("MD추천상품", 1, 1),
    TWO_PLUS_ONE("탄산2+1", 2, 1);

    private final String name;
    private final int buyCount;
    private final int giftCount;

    PromotionType(String name, int buyCount, int giftCount) {
        this.name = name;
        this.buyCount = buyCount;
        this.giftCount = giftCount;
    }

    public static PromotionType from(String name) {
        return Arrays.stream(values())
                .filter(type -> type.name.equals(name))
                .findFirst()
                .orElseThrow(() -> ConvenienceStoreException.from(ErrorMessage.PROMOTION_NOT_FOUND));
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
