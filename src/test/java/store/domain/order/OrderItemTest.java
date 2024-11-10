package store.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.products.BaseProduct;
import store.domain.products.Products;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    private OrderItem orderItem;
    private final String productName = "테스트상품";
    private final int quantity = 2;
    private final int productPrice = 1000;

    @BeforeEach
    void setUp() {
        BaseProduct product = new BaseProduct(productName, productPrice, quantity);
        Products.addProduct(product);
        orderItem = new OrderItem(productName, quantity);
    }

    @DisplayName("주문 상품이 정상적으로 생성되어야 한다")
    @Test
    void 주문_상품이_정상적으로_생성되어야_한다() {
        // when & then
        assertThat(orderItem.getProductName()).isEqualTo(productName);
        assertThat(orderItem.getQuantity()).isEqualTo(quantity);
    }

    @DisplayName("주문 수량을 1개 증가시킬 수 있다")
    @Test
    void 주문_수량을_1개_증가시킬_수_있다() {
        // given
        int expectedQuantity = quantity + 1;

        // when
        orderItem.addOrderQuantity();

        // then
        assertThat(orderItem.getQuantity()).isEqualTo(expectedQuantity);
    }

    @DisplayName("주문 상품의 총 금액을 계산할 수 있다")
    @Test
    void 주문_상품의_총_금액을_계산할_수_있다() {
        // given
        int expectedTotal = productPrice * quantity;

        // when
        int total = orderItem.getTotal();

        // then
        assertThat(total).isEqualTo(expectedTotal);
    }

    @DisplayName("수량 증가 후 총 금액이 정상적으로 계산되어야 한다")
    @Test
    void 수량_증가_후_총_금액이_정상적으로_계산되어야_한다() {
        // given
        orderItem.addOrderQuantity();
        int expectedTotal = productPrice * (quantity + 1);

        // when
        int total = orderItem.getTotal();

        // then
        assertThat(total).isEqualTo(expectedTotal);
    }
}
