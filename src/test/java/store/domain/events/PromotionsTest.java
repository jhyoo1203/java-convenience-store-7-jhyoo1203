package store.domain.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.exception.ConvenienceStoreException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PromotionsTest {

    private Promotion promotion;

    @BeforeEach
    void setUp() {
        promotion = Promotion.from(
                "2+1상품",
                2,
                1,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31)
        );
    }

    @DisplayName("존재하지 않는 프로모션명으로 조회시 예외가 발생한다")
    @Test
    void 존재하지_않는_프로모션명으로_조회시_예외가_발생한다() {
        // given
        String nonExistentName = "존재하지 않는 프로모션";

        // when & then
        assertThatThrownBy(() -> Promotions.findByName(nonExistentName))
                .isInstanceOf(ConvenienceStoreException.class);
    }

    @DisplayName("이미 추가된 프로모션을 이름으로 찾을 수 있다")
    @Test
    void 이미_추가된_프로모션을_이름으로_찾을_수_있다() {
        // given
        Promotions.addPromotion(promotion);

        // when
        Promotion foundPromotion = Promotions.findByName("2+1상품");

        // then
        assertThat(foundPromotion.getName()).isEqualTo("2+1상품");
        assertThat(foundPromotion.getBuyCount()).isEqualTo(2);
        assertThat(foundPromotion.getGiftCount()).isEqualTo(1);
    }
}
