package store.domain.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class MembershipSaleTest {

    private MembershipSale membershipSale;

    @BeforeEach
    void setUp() {
        membershipSale = new MembershipSale();
    }

    @DisplayName("할인률(30%)에 맞게 할인 금액을 반환해야 한다.")
    @ParameterizedTest
    @ValueSource(ints = {3000, 2000, 5000})
    void 할인률에_맞게_할인_금액을_반환해야_한다(int amount) {
        // given
        int expected = (int) (amount * (30.0 / 100));

        // when
        int discountAmount = membershipSale.discount(amount);

        // then
        assertThat(discountAmount).isEqualTo(expected);
    }

    @DisplayName("할인 금액이 최대 할인 금액(8000원)을 초과하면 최대 할인 금액을 반환해야 한다")
    @ParameterizedTest
    @ValueSource(ints = {100000, 200000, 300000})
    void 할인_금액이_최대_할인_금액을_초과하면_최대_할인_금액을_반환해야_한다(int amount) {
        // given
        int expected = 8000;

        // when
        int discountAmount = membershipSale.discount(amount);

        // then
        assertThat(discountAmount).isEqualTo(expected);
    }

}
