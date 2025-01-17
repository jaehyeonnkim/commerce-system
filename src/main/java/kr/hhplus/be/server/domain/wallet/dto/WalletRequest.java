package kr.hhplus.be.server.domain.wallet.dto;

import lombok.*;

@Getter @Setter
@ToString
@Builder
@AllArgsConstructor
public class WalletRequest {
    private Long userId;

}
