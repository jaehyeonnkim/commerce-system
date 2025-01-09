package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.api.coupon.dto.CouponResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueCouponJpaRepository extends JpaRepository<IssueCoupon, Long> {
}