package kr.hhplus.be.server.domain.wallet.service;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import kr.hhplus.be.server.api.wallet.dto.WalletResponse;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import kr.hhplus.be.server.domain.wallet.model.Wallet;
import kr.hhplus.be.server.domain.wallet.repository.WalletJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletService {


    private final WalletJpaRepository walletJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public WalletResponse getBalance(Long userId) {
        User user = findByIdOrThrow(userJpaRepository, userId, "사용자를 찾을 수 없습니다.");
        Wallet wallet = walletJpaRepository.findBalanceById(userId)
                .orElseThrow(() -> new IllegalArgumentException("지갑을 찾을 수 없습니다."));

        return new WalletResponse(wallet);
    }


    /**
     * 공통 메서드
     */
    private <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, String errorMessage) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }
}
