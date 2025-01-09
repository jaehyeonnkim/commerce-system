package kr.hhplus.be.server.api.payment.dto;

import kr.hhplus.be.server.domain.payment.model.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class PaymentResponse {
    private Long id;
    private PaymentStatus status;
    private int totalAmount;
    private String message;
}
