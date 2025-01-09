package kr.hhplus.be.server.api.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.api.coupon.dto.CouponRequest;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import kr.hhplus.be.server.domain.coupon.model.CouponStatus;
import kr.hhplus.be.server.domain.coupon.model.CouponType;
import kr.hhplus.be.server.domain.coupon.repository.CouponJpaRepository;
import kr.hhplus.be.server.domain.coupon.repository.IssueCouponJpaRepository;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CouponControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CouponJpaRepository couponJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private IssueCouponJpaRepository issueCouponJpaRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        userJpaRepository.deleteAll();
        couponJpaRepository.deleteAll();
        issueCouponJpaRepository.deleteAll();

        User user = new User(1L, "김재현");
        userJpaRepository.save(user);

        Coupon coupon = new Coupon(1L, CouponType.FIXED, 5000, 10, 0, CouponStatus.ACTIVE, "TEST COUPON");
        coupon.setCreatedAt(LocalDateTime.now());
        coupon.setUpdatedAt(LocalDateTime.now());
        couponJpaRepository.save(coupon);
    }

    @Test
    public void 쿠폰_발급_통합_테스트() throws Exception {
        // given
        CouponRequest couponRequest = new CouponRequest(1L, 1L);

        // when & then
        MvcResult result = mockMvc.perform(post("/api/coupon/1/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(couponRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.couponId", is(1)))
                .andReturn();

        System.out.println("POST - /api/coupon/1/issue " + result.getResponse().getStatus() + " OK");
    }
}