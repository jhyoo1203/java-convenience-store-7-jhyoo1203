package store.domain.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionTest {

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

    @DisplayName("프로모션 기간 내의 날짜인 경우 true를 반환해야 한다")
    @Test
    void 프로모션_기간_내의_날짜인_경우_true를_반환해야_한다() {
        // given
        LocalDate date = LocalDate.of(2024, 6, 15);

        // when
        boolean validDate = promotion.isValidDate(date);

        // then
        assertThat(validDate).isTrue();
    }

    @DisplayName("프로모션 시작일 이전 날짜인 경우 false를 반환해야 한다")
    @Test
    void 프로모션_시작일_이전_날짜인_경우_false를_반환해야_한다() {
        // given
        LocalDate prevDate = LocalDate.of(2023, 12, 31);

        // when
        boolean validDate = promotion.isValidDate(prevDate);

        // then
        assertThat(validDate).isFalse();
    }

    @DisplayName("프로모션 종료일 이후 날짜인 경우 false를 반환해야 한다")
    @Test
    void 프로모션_종료일_이후_날짜인_경우_false를_반환해야_한다() {
        // given
        LocalDate afterDate = LocalDate.of(2025, 1, 1);

        // when
        boolean validDate = promotion.isValidDate(afterDate);

        // then
        assertThat(validDate).isFalse();
    }
}
