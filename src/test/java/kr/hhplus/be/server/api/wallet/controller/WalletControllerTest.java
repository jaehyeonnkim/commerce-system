package kr.hhplus.be.server.api.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import kr.hhplus.be.server.domain.wallet.dto.WalletRequest;
import kr.hhplus.be.server.domain.wallet.dto.WalletResponse;
import kr.hhplus.be.server.domain.wallet.model.TransactionType;
import kr.hhplus.be.server.domain.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class WalletControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(WalletControllerTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WalletService walletService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userJpaRepository.deleteAll();
    }

    @Test
    public void 잔액_조회_통합_테스트() throws Exception {
        // given
        WalletResponse walletResponse = new WalletResponse(1L,  10000);

        when(walletService.getBalance(1L)).thenReturn(walletResponse);

        // when & then
        MvcResult result = mockMvc.perform(get("/api/wallet/1/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId", is(1)))
                .andExpect(jsonPath("$.balance", is(10000)))
                .andExpect(jsonPath("$.message", is("잔액 조회 성공")))
                .andReturn();

        logger.info("GET - /api/wallet/1/ Status: {}", result.getResponse().getStatus());

    }

    @Test
    public void 잔액_충전_통합_테스트() throws Exception {
        // given
        WalletRequest walletRequest = new WalletRequest(1L, TransactionType.CHARGE,10000);
        WalletResponse walletResponse = new WalletResponse(1L,  10000);
        User user = new User(1L, "김재현");

        when(walletService.chargePoint(user, walletRequest)).thenReturn(walletResponse);

        // when & then
        MvcResult result = mockMvc.perform(post("/api/wallet/1/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.balance", is(15000)))
                .andExpect(jsonPath("$.message", is("충전에 성공하였습니다")))
                .andReturn();

        System.out.println("POST - /api/wallet/1/charge " + result.getResponse().getStatus() + " OK");
    }
}