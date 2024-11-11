package store.domain.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.events.Promotion;
import store.domain.events.Promotions;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionProductTest {

    private static final int PRICE = 1000;
    private static final int INITIAL_STOCK = 10;
    private PromotionProduct promotionProduct;
    private static final String PROMOTION_NAME = "3+1상품";

    @BeforeEach
    void setUp() {
        LocalDate today = LocalDate.now();
        Promotion promotion = Promotion.from(PROMOTION_NAME, 3, 1, today, today.plusDays(7));
        Promotions.addPromotion(promotion);

        promotionProduct = new PromotionProduct("콜라", PRICE, INITIAL_STOCK, PROMOTION_NAME);
    }

    @DisplayName("프로모션 상품이 정상적으로 생성되어야 한다")
    @Test
    void 프로모션_상품이_정상적으로_생성되어야_한다() {
        // then
        assertThat(promotionProduct.getName()).isEqualTo("콜라");
        assertThat(promotionProduct.getPrice()).isEqualTo(PRICE);
        assertThat(promotionProduct.getStockQuantity()).isEqualTo(INITIAL_STOCK);
        assertThat(promotionProduct.getPromotionName()).isEqualTo(PROMOTION_NAME);
    }

    @DisplayName("구매 수량에 따른 증정 수량을 계산할 수 있다")
    @ParameterizedTest
    @CsvSource({
            "4, 1",
            "8, 2",
            "2, 0",
    })
    void 구매_수량에_따른_증정_수량을_계산할_수_있다(int quantity, int expectedGiftCount) {
        // when
        int giftCount = promotionProduct.calculateGiftCount(quantity);

        // then
        assertThat(giftCount).isEqualTo(expectedGiftCount);
    }

    @DisplayName("재고보다 많은 수량 주문시 가능한 최대 증정 수량을 계산한다")
    @Test
    void 재고보다_많은_수량_주문시_가능한_최대_증정_수량을_계산한다() {
        // given
        int excessiveQuantity = INITIAL_STOCK + 5;

        // when
        int giftCount = promotionProduct.calculateGiftCount(excessiveQuantity);

        // then
        int maxPossibleSets = INITIAL_STOCK / 4;
        assertThat(giftCount).isEqualTo(maxPossibleSets);
    }

    @DisplayName("재고 수량이 부족할 경우 구매시 재고가 0이 된다")
    @Test
    void 재고_수량이_부족할_경우_구매시_재고가_0이_된다() {
        // given
        int excessiveQuantity = INITIAL_STOCK + 5;

        // when
        promotionProduct.purchase(excessiveQuantity);

        // then
        assertThat(promotionProduct.getStockQuantity()).isZero();
    }

    @DisplayName("구매 수량만큼 재고가 감소한다")
    @ParameterizedTest
    @CsvSource({
            "3",
            "5",
            "7"
    })
    void 구매_수량만큼_재고가_감소한다(int quantity) {
        // when
        promotionProduct.purchase(quantity);

        // then
        assertThat(promotionProduct.getStockQuantity()).isEqualTo(INITIAL_STOCK - quantity);
    }

    @DisplayName("프로모션 미적용 수량을 계산할 수 있다")
    @Test
    void 프로모션_미적용_수량을_계산할_수_있다() {
        // given
        int stockQuantity = 7;
        PromotionProduct product = new PromotionProduct("콜라", PRICE, stockQuantity, PROMOTION_NAME);

        // when
        int nonPromotionQuantity = product.getNonPromotionQuantity();

        // then
        assertThat(nonPromotionQuantity).isEqualTo(3);
    }
}
