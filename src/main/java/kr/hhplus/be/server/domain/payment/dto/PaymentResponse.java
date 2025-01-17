package kr.hhplus.be.server.domain.payment.dto;

import kr.hhplus.be.server.domain.payment.model.PaymentStatus;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private PaymentStatus status;
    private int totalAmount;
}
