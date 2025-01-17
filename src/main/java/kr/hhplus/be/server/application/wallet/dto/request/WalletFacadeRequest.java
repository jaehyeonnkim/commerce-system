package kr.hhplus.be.server.application.wallet.dto.request;

import kr.hhplus.be.server.domain.wallet.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WalletFacadeRequest {
    private Long userId;
    private TransactionType type;
    private int amount;
}
