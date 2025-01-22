package kr.hhplus.be.server.domain.wallet.dto;

import kr.hhplus.be.server.application.wallet.dto.response.WalletFacadeResponse;
import kr.hhplus.be.server.domain.product.dto.ProductResponse;
import kr.hhplus.be.server.domain.product.model.Product;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
public class WalletResponse {
    private Long walletId;
    private int balance;

    public static WalletResponse toResponse(WalletFacadeResponse result) {
        return WalletResponse.builder()
                .walletId(result.getWalletId())
                .balance(result.getBalance())
                .build();
    }
}
