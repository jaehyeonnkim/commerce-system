package kr.hhplus.be.server.api.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.api.order.dto.OrderRequest;
import kr.hhplus.be.server.api.order.dto.OrderResponse;
import kr.hhplus.be.server.domain.coupon.repository.IssueCouponJpaRepository;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class OrderControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private IssueCouponJpaRepository issueCouponJpaRepository;

    @Mock
    private OrderService orderService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        userJpaRepository.deleteAll();
        issueCouponJpaRepository.deleteAll();

        User user = new User(1L, "김재현");
        userJpaRepository.save(user);
    }

    @Test
    public void 주문_통합_테스트() throws Exception {
        // given
        OrderRequest orderRequest = new OrderRequest(1L, 1L, 2);
        OrderResponse orderResponse = new OrderResponse(1L, 1000);

        when(orderService.order(any(OrderRequest.class))).thenReturn(orderResponse);

        // when & then
        MvcResult result = mockMvc.perform(post("/api/order/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.message", is("주문 성공")))
                .andReturn();

        System.out.println("POST - /api/order/1 " + result.getResponse().getStatus() + " OK");
    }
}
