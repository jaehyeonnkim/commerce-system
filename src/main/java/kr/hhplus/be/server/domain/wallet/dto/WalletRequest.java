package kr.hhplus.be.server.domain.wallet.dto;

import kr.hhplus.be.server.domain.wallet.model.TransactionType;
import lombok.*;

@Getter @Setter
@ToString
@Builder
@AllArgsConstructor
public class WalletRequest {
    private Long userId;
    private TransactionType type;
    private int amount;
}
