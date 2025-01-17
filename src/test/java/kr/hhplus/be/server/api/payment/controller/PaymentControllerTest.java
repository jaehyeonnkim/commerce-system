package kr.hhplus.be.server.api.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import kr.hhplus.be.server.domain.coupon.model.CouponStatus;
import kr.hhplus.be.server.domain.coupon.model.CouponType;
import kr.hhplus.be.server.domain.order.model.Order;
import kr.hhplus.be.server.domain.payment.dto.PaymentRequest;
import kr.hhplus.be.server.domain.payment.dto.PaymentResponse;
import kr.hhplus.be.server.domain.payment.model.PaymentStatus;
import kr.hhplus.be.server.domain.payment.model.PaymentType;
import kr.hhplus.be.server.domain.payment.service.PaymentService;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class PaymentControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(PaymentControllerTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PaymentService paymentService;

    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userJpaRepository.deleteAll(); // 기존 데이터 삭제
    }

    @Test
    public void 결제_통합_테스트() throws Exception {
        //userJpaRepository.save(new User(1L, "김재현"));
        // given
        PaymentRequest paymentRequest = new PaymentRequest(1L, 1L, 1L, PaymentType.CARD);
        PaymentResponse paymentResponse = new PaymentResponse(PaymentStatus.PAID,10000);
        User user = new User(1L, "김재현");
        Order order = new Order();
        Coupon coupon = new Coupon(1L, CouponType.FIXED, 2000, 100, 0, CouponStatus.ACTIVE, "TEST_COUPON");

        when(paymentService.payOrder(user,order,coupon,paymentRequest)).thenReturn(paymentResponse);

        // when & then
        MvcResult result = mockMvc.perform(post("/api/pay/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.totalAmount", is(10000)))
                .andExpect(jsonPath("$.status", is("PAID")))
                .andReturn();

        logger.info("GET - /api/pay Status: {}", result.getResponse().getStatus());
    }
}