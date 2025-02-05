package kr.hhplus.be.server.domain.wallet.service;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.domain.wallet.dto.WalletRequest;
import kr.hhplus.be.server.domain.wallet.dto.WalletResponse;
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
        WalletResponse response = walletService.getBalance(userId);

        // then
        assertNotNull(response);
        assertEquals(10000, response.getBalance());
    }

    @Test
    @DisplayName(" 사용자 정보 없을 때 WALLET_NOT_FOUND 반환 ")
    public void 잔액_조회_실패_사용자정보_없음() {
        // given
        Long userId = 1L;
        when(userJpaRepository.findById(userId)).thenReturn(Optional.empty());

        // when & then
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> walletService.getBalance(userId)
        );

        assertEquals(ErrorCode.WALLET_NOT_FOUND.getCode(), exception.getErrorCode());
        assertEquals(ErrorCode.WALLET_NOT_FOUND.getMessage(), exception.getMessage());
    }


    @Test
    @DisplayName("잔액 충전 성공")
    public void 충전_성공() {
        WalletRequest walletRequest = new WalletRequest(1L, TransactionType.CHARGE, 10000);
        User user = new User(1L, "김재현");

        Wallet wallet = Wallet.createNewWallet(user, 10000, 0, TransactionType.CHARGE);

        when(walletJpaRepository.findBalanceById(walletRequest.getUserId()))
                .thenReturn(Optional.of(wallet));
        when(walletJpaRepository.save(any(Wallet.class)))
                .thenReturn(wallet);

        // when & then
        WalletResponse responseEntity = walletService.chargePoint(user, walletRequest);

        assertNotNull(responseEntity);
        assertEquals(10000, responseEntity.getBalance());

    }

    @Test
    @DisplayName("사용자 정보 없을 때 WALLET_NOT_FOUND 반환 ")
    public void 충전_실패_사용자정보_없음() {
        // given
        WalletRequest walletRequest =  new WalletRequest(1L, TransactionType.CHARGE, 5000);
        User user = null;
        when(userJpaRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> walletService.chargePoint(user, walletRequest)
        );

        // 예외의 에러 코드 확인
        assertEquals(ErrorCode.WALLET_NOT_FOUND.getCode(), exception.getErrorCode());
        assertEquals(ErrorCode.WALLET_NOT_FOUND.getMessage(), exception.getMessage());
    }
}