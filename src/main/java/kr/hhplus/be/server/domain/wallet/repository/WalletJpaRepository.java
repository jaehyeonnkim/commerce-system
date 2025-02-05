package kr.hhplus.be.server.domain.wallet.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletJpaRepository extends JpaRepository<Wallet, Long> {
    @Query("SELECT w FROM Wallet w WHERE w.user.id = :userId ORDER BY w.createdAt DESC LIMIT 1")
    Optional<Wallet> findBalanceById(@Param("userId") Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM Wallet w WHERE w.user.id = :userId ORDER BY w.createdAt DESC LIMIT 1")
    Optional<Wallet> findByIdWithLock(@Param("userId") Long userId);

}
