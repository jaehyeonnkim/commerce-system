package kr.hhplus.be.server.api.wallet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.api.wallet.dto.WalletRequest;
import kr.hhplus.be.server.api.wallet.dto.WalletResponse;
import kr.hhplus.be.server.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
@Tag(name = "지갑 API", description = "지갑을 관리하는 API")
public class WalletController {
    private final WalletService walletService;

    //잔액 조회
    @Operation(summary = "잔액 조회")
    @GetMapping("/{userId}/")
    public WalletResponse getBalance(@PathVariable Long userId) {
        return walletService.getBalance(userId);
    }

    //잔액 충전
    @Operation(summary = "잔액 충전")
    @PostMapping("/{userId}/charge")
    public ResponseEntity<WalletResponse> chargeUserBalance(@RequestBody WalletRequest walletRequest) {
        return walletService.chargePoint(walletRequest);
    }
}