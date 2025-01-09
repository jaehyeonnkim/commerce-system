package kr.hhplus.be.server.api.wallet.controller;

import kr.hhplus.be.server.api.coupon.dto.CouponResponse;
import kr.hhplus.be.server.api.user.dto.UserRequest;
import kr.hhplus.be.server.api.wallet.dto.WalletResponse;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    //잔액 조회
    @GetMapping("/{userId}/")
    public WalletResponse getBalance(@PathVariable Long userId) {
        return walletService.getBalance(userId);
    }

    //잔액 충전
    @PostMapping("/{userId}/charge")
    public ResponseEntity<Object> chargeUserBalance(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        Map<String, Object> response = Map.of(
                "balance", 15000,
                "message", "잔액이 성공적으로 충전됐습니다.",
                "userId", userId,
                "chargedAmount", 30000
        );
        return ResponseEntity.ok(response);
    }
}