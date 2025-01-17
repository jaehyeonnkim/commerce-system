package kr.hhplus.be.server.api.wallet.dto;

import kr.hhplus.be.server.domain.wallet.model.TransactionType;
import kr.hhplus.be.server.domain.wallet.model.Wallet;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequest {
    private Long userId;
    private TransactionType type;
    private int amount;
}
