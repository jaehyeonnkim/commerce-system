package kr.hhplus.be.server.application.wallet.dto.response;

import kr.hhplus.be.server.domain.wallet.dto.WalletResponse;
import kr.hhplus.be.server.application.payment.dto.response.PaymentFacadeResponse;
import kr.hhplus.be.server.domain.payment.dto.PaymentResponse;
import kr.hhplus.be.server.domain.payment.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WalletFacadeResponse {
    private Long walletId;
    private int balance;

    public static WalletFacadeResponse toResponse(WalletResponse response) {
        return WalletFacadeResponse.builder()
                .walletId(response.getWalletId())
                .balance(response.getBalance())
                .build();
    }
}
