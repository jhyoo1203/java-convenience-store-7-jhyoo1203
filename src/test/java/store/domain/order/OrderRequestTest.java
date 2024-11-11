package store.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.products.BaseProduct;
import store.domain.products.Products;
import store.global.exception.ConvenienceStoreException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderRequestTest {

    private final String cola = "콜라";
    private final String cider = "사이다";
    private final int colaPrice = 1500;
    private final int ciderPrice = 1000;

    @BeforeEach
    void setUp() {
        Products.addProduct(new BaseProduct(cola, colaPrice, 10));
        Products.addProduct(new BaseProduct(cider, ciderPrice, 10));
    }

    @DisplayName("입력 문자열로부터 주문 요청을 생성할 수 있다")
    @Test
    void 입력_문자열로부터_주문_요청을_생성할_수_있다() {
        // given
        String input = "[콜라-3],[사이다-2]";

        // when
        OrderRequest orderRequest = OrderRequest.from(input);

        // then
        assertThat(orderRequest.getItems()).hasSize(2);
        assertThat(orderRequest.findByName(cola).getQuantity()).isEqualTo(3);
        assertThat(orderRequest.findByName(cider).getQuantity()).isEqualTo(2);
    }

    @DisplayName("상품명으로 주문 상품을 찾을 수 있다")
    @Test
    void 상품명으로_주문_상품을_찾을_수_있다() {
        // given
        OrderRequest orderRequest = OrderRequest.from("[콜라-3],[사이다-2]");

        // when
        OrderItem orderItem = orderRequest.findByName(cola);

        // then
        assertThat(orderItem.getProductName()).isEqualTo(cola);
        assertThat(orderItem.getQuantity()).isEqualTo(3);
    }

    @DisplayName("존재하지 않는 상품명으로 조회시 예외가 발생한다")
    @Test
    void 존재하지_않는_상품명으로_조회시_예외가_발생한다() {
        // given
        OrderRequest orderRequest = OrderRequest.from("[콜라-3],[사이다-2]");
        String nonExistentProduct = "환타";

        // when & then
        assertThatThrownBy(() -> orderRequest.findByName(nonExistentProduct))
                .isInstanceOf(ConvenienceStoreException.class);
    }

    @DisplayName("전체 주문 수량을 계산할 수 있다")
    @Test
    void 전체_주문_수량을_계산할_수_있다() {
        // given
        OrderRequest orderRequest = OrderRequest.from("[콜라-3],[사이다-2]");
        int expectedTotalQuantity = 5;

        // when
        int totalQuantity = orderRequest.calculateTotalQuantity();

        // then
        assertThat(totalQuantity).isEqualTo(expectedTotalQuantity);
    }

    @DisplayName("전체 주문 금액을 계산할 수 있다")
    @Test
    void 전체_주문_금액을_계산할_수_있다() {
        // given
        OrderRequest orderRequest = OrderRequest.from("[콜라-3],[사이다-2]");
        int expectedTotalAmount = (colaPrice * 3) + (ciderPrice * 2);

        // when
        int totalAmount = orderRequest.calculateTotalAmount();

        // then
        assertThat(totalAmount).isEqualTo(expectedTotalAmount);
    }
}
