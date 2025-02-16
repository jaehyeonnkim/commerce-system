package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.domain.order.dto.OrderRequest;
import kr.hhplus.be.server.domain.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.order.model.Order;
import kr.hhplus.be.server.domain.order.repository.OrderJpaRepository;
import kr.hhplus.be.server.domain.order.repository.OrderProductJpaRepository;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class )
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    private OrderJpaRepository orderJpaRepository;

    @Mock
    private OrderProductJpaRepository orderProductJpaRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("사용자 정보 없을 때 USER_NOT_FOUND")
    void 주문_실패_사용자정보_없음() {
        //given
        OrderRequest orderRequest = new OrderRequest(1L, 1, 1L);
        User user = new User();
        Product product = Product.builder().name("멋진가방").price(10000).stock(100).build();

        when(userJpaRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> orderService.orderProducts(user, product, orderRequest)
        );

        // 예외의 에러 코드 확인
        assertEquals(ErrorCode.USER_NOT_FOUND.getCode(), exception.getErrorCode());
        assertEquals(ErrorCode.USER_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("주문 정보 없을 때 ORDER_NOT_FOUND ")
    void 주문_실패_주문정보_없음() {
        // given
        OrderRequest orderRequest = new OrderRequest(1L, 1, 1L);
        User user = new User(1L, "김재현");
        Product product = Product.builder().name("멋진가방").price(10000).stock(100).build();


        when(orderJpaRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        BusinessException exception = assertThrows(
                BusinessException.class,
                () ->  orderService.orderProducts(user, product, orderRequest)
        );

        // 예외의 에러 코드 확인
        assertEquals(ErrorCode.ORDER_NOT_FOUND.getCode(), exception.getErrorCode());
        assertEquals(ErrorCode.ORDER_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("주문 성공")
    public void 주문성공() {
        // given
        OrderRequest orderRequest = new OrderRequest(1L, 2, 1L);
        User user = new User(1L, "김재현");
        Product product = Product.builder().name("멋진가방").price(10000).stock(100).build();


        when(userJpaRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderJpaRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        OrderResponse orderResponse = orderService.orderProducts(user, product, orderRequest);

        // then
        assertNotNull(orderResponse);
        assertEquals(10000 * 2,orderResponse.getTotalAmount() ); //
        assertEquals(98, product.getStock()); //
    }


}