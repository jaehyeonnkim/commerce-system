package kr.hhplus.be.server.api.wallet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.wallet.dto.request.WalletFacadeRequest;
import kr.hhplus.be.server.application.wallet.dto.response.WalletFacadeResponse;
import kr.hhplus.be.server.application.wallet.facade.WalletFacade;
import kr.hhplus.be.server.common.dto.ApiResponse;
import kr.hhplus.be.server.domain.wallet.dto.WalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
@Tag(name = "지갑 API", description = "지갑을 관리하는 API")
public class WalletController {

    private final WalletFacade walletFacade;

    //잔액 조회
    @Operation(summary = "잔액 조회")
    @GetMapping("/{userId}/")
    public ApiResponse<WalletResponse> getBalance(@RequestParam Long userId) {
        WalletFacadeResponse result = walletFacade.getBalance(userId);

        return ApiResponse.ok(WalletResponse.toResponse(result), LocalDateTime.now());
    }

    //잔액 충전
    @Operation(summary = "잔액 충전")
    @PostMapping("/{userId}/charge")
    public ApiResponse<WalletResponse> chargeUserBalance(@RequestBody WalletFacadeRequest walletRequest) {
        WalletFacadeResponse result =  walletFacade.chargePoint(walletRequest);
        return ApiResponse.ok(WalletResponse.toResponse(result), LocalDateTime.now());
    }
}