package kr.hhplus.be.server.infra.coupon;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.hhplus.be.server.api.coupon.dto.CouponResponse;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponRepository;
import kr.hhplus.be.server.domain.coupon.IssueCoupon;
import kr.hhplus.be.server.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

}
