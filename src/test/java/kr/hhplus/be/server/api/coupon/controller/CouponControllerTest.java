package kr.hhplus.be.server.api.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.coupon.dto.CouponRequest;
import kr.hhplus.be.server.domain.coupon.dto.CouponResponse;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import kr.hhplus.be.server.domain.coupon.model.CouponStatus;
import kr.hhplus.be.server.domain.coupon.model.CouponType;
import kr.hhplus.be.server.domain.coupon.sersvice.CouponService;
import kr.hhplus.be.server.domain.user.model.User;
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
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CouponControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(CouponControllerTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CouponService couponService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


    }

    @Test
    public void 쿠폰_발급_통합_테스트() throws Exception {
        // given
        CouponRequest couponRequest = new CouponRequest(1L, 1L);
        CouponResponse couponResponse = new CouponResponse(
                1L,"새해쿠폰", "TEST_COUPON",CouponType.FIXED, 2000,false, LocalDateTime.now(), null,LocalDateTime.now());

        User user = new User(1L, "김재현");
        Coupon coupon = new Coupon(1L,CouponType.FIXED, 2000, 100, 0, CouponStatus.ACTIVE, "TEST_COUPON");
        when(couponService.issueCoupon(user, coupon, couponRequest)).thenReturn(couponResponse);
        // when & then
        MvcResult result = mockMvc.perform(post("/api/coupon/1/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(couponRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("TEST_COUPON")))
                .andReturn();

        logger.info("GET - /api/coupon/{userid}/issue Status: {}", result.getResponse().getStatus());
    }
}