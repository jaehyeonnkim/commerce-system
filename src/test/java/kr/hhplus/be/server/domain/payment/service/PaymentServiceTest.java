package kr.hhplus.be.server.domain.payment.service;

import kr.hhplus.be.server.api.payment.dto.PaymentRequest;
import kr.hhplus.be.server.api.payment.dto.PaymentResponse;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import kr.hhplus.be.server.domain.coupon.model.CouponStatus;
import kr.hhplus.be.server.domain.coupon.model.CouponType;
import kr.hhplus.be.server.domain.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.domain.order.model.Order;
import kr.hhplus.be.server.domain.order.model.OrderProduct;
import kr.hhplus.be.server.domain.order.repository.OrderJpaRepository;
import kr.hhplus.be.server.domain.order.repository.OrderProductJpaRepository;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.payment.model.Payment;
import kr.hhplus.be.server.domain.payment.model.PaymentStatus;
import kr.hhplus.be.server.domain.payment.model.PaymentType;
import kr.hhplus.be.server.domain.payment.repository.PaymentJpaRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class )
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private OrderJpaRepository orderJpaRepository;

    @Mock
    private CouponJpaRepository couponJpaRepository;

    @Mock
    private PaymentJpaRepository paymentJpaRepository;

    @Test
    @DisplayName("쿠폰이 없을 때 결제 성공")
    public void 결제_성공_without_쿠폰() {
        // given
        PaymentRequest paymentRequest = new PaymentRequest(1L, 1L, null, PaymentType.CARD);
        User user = new User(1L, "김재현");
        Product product = new Product(1L, "멋진가방", 10000, 100, 0, LocalDateTime.now(), LocalDateTime.now());
        OrderProduct orderProduct = OrderProduct.createOrderItem(product, 10000, 2);
        Order order = Order.createOrder(user,orderProduct);

        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderJpaRepository.findById(1L)).thenReturn(Optional.of(order));
        when(paymentJpaRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        PaymentResponse paymentResponse = paymentService.payOrder(paymentRequest);

        // then
        assertNotNull(paymentResponse);
        assertEquals(20000, paymentResponse.getTotalAmount());
        assertEquals(PaymentStatus.PAID, paymentResponse.getStatus());
    }

    @Test
    @DisplayName("쿠폰이 있을 때 결제 성공")
    public void 결제_성공_with_쿠폰() {
        // given
        PaymentRequest paymentRequest = new PaymentRequest(1L, 1L, 1L, PaymentType.CARD);
        User user = new User(1L, "김재현");
        Product product = new Product(1L, "멋진가방", 20000, 100, 0, LocalDateTime.now(), LocalDateTime.now());
        OrderProduct orderProduct = OrderProduct.createOrderItem(product, 10000, 2);
        Order order = Order.createOrder(user,orderProduct);
        Coupon coupon = new Coupon(1L, CouponType.FIXED, 5000, 100, 0, CouponStatus.ACTIVE, "TEST COUPON");

        // 사용자, 주문, 쿠폰 정상 조회 설정
        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderJpaRepository.findById(1L)).thenReturn(Optional.of(order));
        when(couponJpaRepository.findById(1L)).thenReturn(Optional.of(coupon));
        when(paymentJpaRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        PaymentResponse paymentResponse = paymentService.payOrder(paymentRequest);

        // then
        assertNotNull(paymentResponse);
        assertEquals(15000, paymentResponse.getTotalAmount()); // 총 금액 = 20000 - 5000 (쿠폰 할인 적용)
        assertEquals(PaymentStatus.PAID, paymentResponse.getStatus());
    }

    @Test
    @DisplayName("사용자 정보 없을 때 IllegalArgumentException 반환")
    public void 결제_실패_사용자정보_없음() {
        // given
        PaymentRequest paymentRequest = new PaymentRequest(1L, 1L, null, PaymentType.CARD);
        when(userJpaRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.payOrder(paymentRequest),
                "사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("주문을 찾을 수 없을 때 IllegalArgumentException 반환")
    public void 결제_실패_주문정보_없음() {
        // given
        PaymentRequest paymentRequest = new PaymentRequest(1L, 1L, null, PaymentType.CARD);
        User user = new User(1L, "김재현");

        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderJpaRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.payOrder(paymentRequest),
                "주문을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("쿠폰정보 없을 때 IllegalArgumentException 반환")
    public void 결제_실패_쿠폰정보_없음() {
        // given
        PaymentRequest paymentRequest = new PaymentRequest(1L, 1L, 1L, PaymentType.CARD);
        User user = new User(1L, "김재현");
        Product product = new Product(1L, "멋진가방", 10000, 100, 0, LocalDateTime.now(), LocalDateTime.now());
        OrderProduct orderProduct = OrderProduct.createOrderItem(product, 10000, 2);
        Order order = Order.createOrder(user,orderProduct);

        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderJpaRepository.findById(1L)).thenReturn(Optional.of(order));
        when(couponJpaRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.payOrder(paymentRequest),
                "쿠폰을 찾을 수 없습니다.");
    }

}