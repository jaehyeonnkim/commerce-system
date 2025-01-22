package kr.hhplus.be.server.domain.coupon.repository;

import kr.hhplus.be.server.domain.coupon.model.IssueCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueCouponJpaRepository extends JpaRepository<IssueCoupon, Long> {

    @Query("SELECT ic FROM IssueCoupon ic WHERE ic.user.id = :userId AND ic.coupon.id = :couponId")
    IssueCoupon findDuplicateCoupon(@Param("userId") Long userId, @Param("couponId") Long couponId);

}