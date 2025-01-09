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
    private int balance;

    public WalletResponse(Wallet wallet) {
        this.walletId = wallet.getId();
        this.balance = wallet.getBalance();
    }

}
