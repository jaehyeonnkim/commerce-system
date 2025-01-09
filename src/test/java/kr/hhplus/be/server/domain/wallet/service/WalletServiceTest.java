package kr.hhplus.be.server.domain.wallet.service;

import kr.hhplus.be.server.api.wallet.dto.WalletRequest;
import kr.hhplus.be.server.api.wallet.dto.WalletResponse;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import kr.hhplus.be.server.domain.wallet.model.TransactionType;
import kr.hhplus.be.server.domain.wallet.model.Wallet;
import kr.hhplus.be.server.domain.wallet.repository.WalletJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static kr.hhplus.be.server.domain.wallet.model.Wallet.createNewWallet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class )
class WalletServiceTest {
    @InjectMocks
    private WalletService walletService;

    @Mock
    private WalletJpaRepository walletJpaRepository;

    @Mock
    private UserJpaRepository userJpaRepository;


    @Test
    @DisplayName("잔액 조회 성공")
    public void 잔액_조회_성공() {
        // given
        Long userId = 1L;
        User user = new User(userId, "김재현");
        Wallet wallet = createNewWallet(user, 5000, 10000, TransactionType.CHARGE);

        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(user));
        when(walletJpaRepository.findBalanceById(userId)).thenReturn(Optional.of(wallet));

        // when
        System.out.println(wallet.getBalance());
        System.out.println(wallet.getAmount());
        WalletResponse response = walletService.getBalance(userId);

        // then
        assertNotNull(response);
        assertEquals(10000, response.getBalance());
    }

    @Test
    @DisplayName(" 사용자 정보 없을 때 IllegalArgumentException 반환 ")
    public void 잔액_조회_실패_사용자정보_없음() {
        // given
        Long userId = 1L;
        when(userJpaRepository.findById(userId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> walletService.getBalance(userId),
                "사용자를 찾을 수 없습니다.");
    }


    @Test
    @DisplayName("잔액 충전 성공")
    public void 충전_성공() {
        WalletRequest walletRequest = new WalletRequest(1L, TransactionType.CHARGE, 5000);
        User user = new User(1L, "김재현");
        Wallet wallet = Wallet.createNewWallet(user, 5000, 10000, TransactionType.CHARGE);

        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
        when(walletJpaRepository.findBalanceById(1L)).thenReturn(Optional.of(wallet));
        when(walletJpaRepository.save(any(Wallet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        ResponseEntity<WalletResponse> responseEntity = walletService.chargePoint(walletRequest);

        // then
        assertNotNull(responseEntity);
        assertEquals(15000, responseEntity.getBody().getBalance());
        assertEquals("충전에 성공하였습니다", responseEntity.getBody().getMessage());
    }

    @Test
    @DisplayName("사용자 정보 없을 때 IllegalArgumentException 반환 ")
    public void 충전_실패_사용자정보_없음() {
        // given
        WalletRequest walletRequest =  new WalletRequest(1L, TransactionType.CHARGE, 5000);
        when(userJpaRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> walletService.chargePoint(walletRequest),
                "사용자를 찾을 수 없습니다.");
    }
}