package kr.hhplus.be.server.api.wallet.dto;

import kr.hhplus.be.server.domain.wallet.model.TransactionType;
import kr.hhplus.be.server.domain.wallet.model.Wallet;
import lombok.*;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {
    private int id;
    private TransactionType type;
    private int amount;
    private int balance;


    public WalletResponse(Wallet wallet) {
        this.balance = wallet.getBalance();
        this.type = wallet.getType();
        this.amount = wallet.getAmount();
    }
}
