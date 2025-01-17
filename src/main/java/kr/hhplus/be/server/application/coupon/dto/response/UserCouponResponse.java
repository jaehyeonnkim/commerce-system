package kr.hhplus.be.server.application.coupon.dto.response;

import kr.hhplus.be.server.domain.coupon.dto.CouponResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponResponse {
    private List<CouponResponse> issuedCouponList;

    public static UserCouponResponse toResponse(UserCouponResponse result) {
        return UserCouponResponse.builder()
                .issuedCouponList(result.getIssuedCouponList())
                .build();
    }
}


