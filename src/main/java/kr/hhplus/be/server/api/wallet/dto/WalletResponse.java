package kr.hhplus.be.server.api.wallet.dto;

import kr.hhplus.be.server.domain.wallet.model.Wallet;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse{
    private Long walletId;
    private String message;

    public WalletResponse(Wallet wallet) {
    }
}
