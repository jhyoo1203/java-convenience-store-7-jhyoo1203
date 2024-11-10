package store.domain.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.global.exception.ConvenienceStoreException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductsTest {

    private static final int PRICE = 1000;
    private static final int INITIAL_STOCK = 10;
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("콜라", PRICE, INITIAL_STOCK);
        Products.addProduct(product);
    }

    @DisplayName("존재하지 않는 상품명으로 조회시 예외가 발생한다")
    @Test
    void 존재하지_않는_상품명으로_조회시_예외가_발생한다() {
        // given
        String nonExistentName = "환타";

        // when & then
        assertThatThrownBy(() -> Products.findByName(nonExistentName))
                .isInstanceOf(ConvenienceStoreException.class);
    }

    @DisplayName("재고가 부족한 경우 구매가 실패해야 한다")
    @Test
    void 재고가_부족한_경우_구매가_실패해야_한다() {
        // given
        int excessiveQuantity = INITIAL_STOCK + 1;
        String orderInput = String.format("[콜라-%d]", excessiveQuantity);

        // when
        Products.purchaseProducts(orderInput);

        // then
        assertThat(product.getStockQuantity()).isEqualTo(INITIAL_STOCK);
    }

    @DisplayName("여러 상품을 한번에 구매할 수 있다")
    @Test
    void 여러_상품을_한번에_구매할_수_있다() {
        // given
        Product anotherProduct = new Product("사이다", PRICE, INITIAL_STOCK);
        Products.addProduct(anotherProduct);
        String orderInput = "[콜라-2],[사이다-3]";

        // when
        Products.purchaseProducts(orderInput);

        // then
        assertThat(product.getStockQuantity()).isEqualTo(INITIAL_STOCK - 2);
        assertThat(anotherProduct.getStockQuantity()).isEqualTo(INITIAL_STOCK - 3);
    }

}
