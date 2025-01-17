package kr.hhplus.be.server.application.wallet.facade;

import kr.hhplus.be.server.api.wallet.dto.WalletRequest;
import kr.hhplus.be.server.application.wallet.dto.request.WalletFacadeRequest;
import kr.hhplus.be.server.application.wallet.dto.response.WalletFacadeResponse;
import kr.hhplus.be.server.domain.coupon.sersvice.CouponService;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.product.service.ProductService;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.service.UserService;
import kr.hhplus.be.server.domain.wallet.dto.WalletResponse;
import kr.hhplus.be.server.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletFacade {

    private final ProductService productService;
    private final CouponService couponService;
    private final UserService userService;
    private final OrderService orderService;
    private final WalletService walletService;


    @Transactional
    public WalletFacadeResponse getBalance(Long userId) {
        User user = userService.getUserById(userId);

        WalletResponse Response = walletService.getBalance(user.getId());

        return WalletFacadeResponse.toResponse(Response);
    }

    @Transactional
    public WalletFacadeResponse chargePoint(WalletFacadeRequest request) {
        User user = userService.getUserById(request.getUserId());
        WalletResponse Response = walletService.chargePoint(
                user,
                new WalletRequest(
                    user.getId(),
                    request.getType(),
                    request.getAmount()
                )
        );
        return WalletFacadeResponse.toResponse(Response);
    }

}
