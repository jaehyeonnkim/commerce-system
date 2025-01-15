package kr.hhplus.be.server.domain.wallet.repository;

import kr.hhplus.be.server.domain.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletJpaRepository extends JpaRepository<Wallet, Long> {
    @Query(value = "SELECT * FROM wallet WHERE user_id = :userId ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<Wallet> findBalanceById(@Param("userId") Long userId);
}
