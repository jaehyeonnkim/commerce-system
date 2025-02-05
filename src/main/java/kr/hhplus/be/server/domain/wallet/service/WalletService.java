package kr.hhplus.be.server.domain.wallet.service;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.wallet.dto.WalletRequest;
import kr.hhplus.be.server.domain.wallet.dto.WalletResponse;
import kr.hhplus.be.server.domain.wallet.model.TransactionType;
import kr.hhplus.be.server.domain.wallet.model.Wallet;
import kr.hhplus.be.server.domain.wallet.repository.WalletJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletJpaRepository walletJpaRepository;

    //잔액 조회
    @Transactional
    public WalletResponse getBalance(Long userId) {
        Wallet wallet = walletJpaRepository.findByIdWithLock(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WALLET_NOT_FOUND.getCode(), ErrorCode.WALLET_NOT_FOUND.getMessage()));

        return new WalletResponse(wallet.getId(), wallet.getBalance());
    }

    //잔액 충전
    @Transactional
    public WalletResponse chargePoint(User user, WalletRequest walletRequest) {
        int currentBalance = getBalance(user.getId()).getBalance();
        int updatedBalance = currentBalance + walletRequest.getAmount();

        Wallet wallet = Wallet.createNewWallet(user, walletRequest.getAmount(), updatedBalance, TransactionType.CHARGE);
        walletJpaRepository.save(wallet);

        return new WalletResponse(wallet.getId(), updatedBalance);
    }






}
