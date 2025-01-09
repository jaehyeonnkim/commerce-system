package kr.hhplus.be.server.domain.coupon.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.api.coupon.dto.CouponResponse;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
    // 보유 쿠폰 조회
    @Query("SELECT new kr.hhplus.be.server.api.coupon.dto.CouponResponse(c.id, c.name, c.code, c.type, c.value, i.used, i.issuedAt, i.usedAt,c.expiredAt) " +
            "FROM Coupon c JOIN IssueCoupon i ON c.id = i.coupon.id WHERE i.user.id = :userId")
    List<CouponResponse> findAllbyId(@Param("userId") Long userId);

    // 선착순 쿠폰 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.id = :couponId")
    Coupon findByIdWithLock(@Param("couponId") Long couponId);

}