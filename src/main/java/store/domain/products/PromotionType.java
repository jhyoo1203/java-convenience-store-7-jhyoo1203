package store.domain.products;

import store.global.exception.ConvenienceStoreException;
import store.global.exception.ErrorMessage;

import java.util.Arrays;

public enum PromotionType {

    // 증정 프로모션
    MD_PICK("MD추천상품", 1, 1, null),
    TWO_PLUS_ONE("탄산2+1", 2, 1, null),

    // 할인 프로모션
    FLASH_SALE("반짝할인", null, null, 10.0);

    private final String name;
    private final Integer buyCount;
    private final Integer giftCount;
    private final Double saleRate;

    PromotionType(String name, Integer buyCount, Integer giftCount, Double saleRate) {
        this.name = name;
        this.buyCount = buyCount;
        this.giftCount = giftCount;
        this.saleRate = saleRate;
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

    public double getSaleRate() {
        return saleRate;
    }
}
