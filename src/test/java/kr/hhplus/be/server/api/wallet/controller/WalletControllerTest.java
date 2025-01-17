package kr.hhplus.be.server.api.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.api.wallet.dto.WalletRequest;
import kr.hhplus.be.server.api.wallet.dto.WalletResponse;
import kr.hhplus.be.server.domain.wallet.model.TransactionType;
import kr.hhplus.be.server.domain.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class WalletControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private WalletService walletService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("GET - /api/wallet/1/ 200 OK")
    public void testGetBalance_Success() throws Exception {
        // given
        WalletResponse walletResponse = new WalletResponse(1L, "잔액 조회 성공", 10000);

        when(walletService.getBalance(1L)).thenReturn(walletResponse);

        // when & then
        MvcResult result = mockMvc.perform(get("/api/wallet/1/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.balance", is(10000)))
                .andExpect(jsonPath("$.message", is("잔액 조회 성공")))
                .andReturn();

        System.out.println("GET - /api/wallet/1/ " + result.getResponse().getStatus() + " OK");
    }

    @Test
    @DisplayName("POST - /api/wallet/1/charge 200 OK")
    public void testChargeUserBalance_Success() throws Exception {
        // given
        WalletRequest walletRequest = new WalletRequest(1L, TransactionType.CHARGE, 5000);
        WalletResponse walletResponse = new WalletResponse(1L, "잔액 조회 성공", 10000);

        when(walletService.chargePoint(any(WalletRequest.class))).thenReturn(ResponseEntity.ok(walletResponse));

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