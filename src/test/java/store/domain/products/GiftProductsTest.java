package store.domain.products;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GiftProductsTest {

    @AfterEach
    void tearDown() {
        GiftProducts.clear();
    }

    @DisplayName("증정 상품을 추가할 수 있다")
    @ParameterizedTest
    @CsvSource({
            "콜라, 2",
            "사이다, 3",
            "맥주, 1"
    })
    void 증정_상품을_추가할_수_있다(String productName, int quantity) {
        // when
        GiftProducts.addGiftProducts(productName, quantity);
        Set<Map.Entry<String, Integer>> entries = GiftProducts.getGiftEntries();

        // then
        assertThat(entries).hasSize(1);
        Map.Entry<String, Integer> entry = entries.iterator().next();
        assertThat(entry.getKey()).isEqualTo(productName);
        assertThat(entry.getValue()).isEqualTo(quantity);
    }

    @DisplayName("여러 개의 증정 상품을 추가할 수 있다")
    @ParameterizedTest
    @MethodSource("provideMultipleGiftProducts")
    void 여러_개의_증정_상품을_추가할_수_있다(String product1, int quantity1, String product2, int quantity2) {
        // when
        GiftProducts.addGiftProducts(product1, quantity1);
        GiftProducts.addGiftProducts(product2, quantity2);
        Set<Map.Entry<String, Integer>> entries = GiftProducts.getGiftEntries();

        // then
        assertThat(entries).hasSize(2);
        assertThat(entries).anySatisfy(entry -> {
            assertThat(entry.getKey()).isEqualTo(product1);
            assertThat(entry.getValue()).isEqualTo(quantity1);
        });
        assertThat(entries).anySatisfy(entry -> {
            assertThat(entry.getKey()).isEqualTo(product2);
            assertThat(entry.getValue()).isEqualTo(quantity2);
        });
    }

    static Stream<Arguments> provideMultipleGiftProducts() {
        return Stream.of(
                Arguments.of("콜라", 2, "사이다", 3),
                Arguments.of("맥주", 1, "와인", 2),
                Arguments.of("과자", 5, "아이스크림", 3)
        );
    }

    @DisplayName("같은 상품을 다시 추가하면 수량이 갱신된다")
    @ParameterizedTest
    @CsvSource({
            "콜라, 2, 3",
            "사이다, 3, 4",
            "맥주, 1, 5"
    })
    void 같은_상품을_다시_추가하면_수량이_갱신된다(String productName, int initialQuantity, int updatedQuantity) {
        // when
        GiftProducts.addGiftProducts(productName, initialQuantity);
        GiftProducts.addGiftProducts(productName, updatedQuantity);
        Set<Map.Entry<String, Integer>> entries = GiftProducts.getGiftEntries();

        // then
        assertThat(entries).hasSize(1);
        Map.Entry<String, Integer> entry = entries.iterator().next();
        assertThat(entry.getKey()).isEqualTo(productName);
        assertThat(entry.getValue()).isEqualTo(updatedQuantity);
    }

    @DisplayName("증정 상품 목록을 초기화할 수 있다")
    @ParameterizedTest
    @MethodSource("provideProductsForClearTest")
    void 증정_상품_목록을_초기화할_수_있다(String product1, int quantity1, String product2, int quantity2) {
        // given
        GiftProducts.addGiftProducts(product1, quantity1);
        GiftProducts.addGiftProducts(product2, quantity2);

        // when
        GiftProducts.clear();
        Set<Map.Entry<String, Integer>> entries = GiftProducts.getGiftEntries();

        // then
        assertThat(entries).isEmpty();
    }

    static Stream<Arguments> provideProductsForClearTest() {
        return Stream.of(
                Arguments.of("콜라", 2, "사이다", 3),
                Arguments.of("맥주", 1, "와인", 2)
        );
    }
}
