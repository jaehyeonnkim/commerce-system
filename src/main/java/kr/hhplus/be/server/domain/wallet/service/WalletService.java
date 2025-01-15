package kr.hhplus.be.server.domain.wallet.service;

import kr.hhplus.be.server.api.wallet.dto.WalletRequest;
import kr.hhplus.be.server.api.wallet.dto.WalletResponse;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import kr.hhplus.be.server.domain.wallet.model.TransactionType;
import kr.hhplus.be.server.domain.wallet.model.Wallet;
import kr.hhplus.be.server.domain.wallet.repository.WalletJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletService {


    private final WalletJpaRepository walletJpaRepository;
    private final UserJpaRepository userJpaRepository;

    /**
     * 잔액 조회
     */
    public WalletResponse getBalance(Long userId) {
        User user = findByIdOrThrow(userJpaRepository, userId, "사용자를 찾을 수 없습니다.");
        Wallet wallet = walletJpaRepository.findBalanceById(userId)
                .orElseThrow(() -> new IllegalArgumentException("지갑을 찾을 수 없습니다."));

        return new WalletResponse(wallet);
    }

    /**
     * 잔액 충전
     */
    public ResponseEntity<WalletResponse> chargePoint(WalletRequest walletRequest) {
        User user = findByIdOrThrow(userJpaRepository, walletRequest.getUserId(), "사용자를 찾을 수 없습니다.");
        int currentBalance = walletJpaRepository.findBalanceById(walletRequest.getUserId())
                .map(Wallet::getBalance)
                .orElse(0);

        Wallet wallet = Wallet.createNewWallet(user, walletRequest.getAmount(), currentBalance + walletRequest.getAmount(), TransactionType.CHARGE);

        walletJpaRepository.save(wallet);

        WalletResponse response = new WalletResponse(wallet.getId(), "충전에 성공하였습니다", wallet.getBalance());
        return ResponseEntity.ok(response);
    }

    /**
     * 공통 메서드
     */
    private <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, String errorMessage) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }


}
