package kr.hhplus.be.server.api.payment.dto;

import kr.hhplus.be.server.domain.payment.model.PaymentStatus;
import kr.hhplus.be.server.domain.payment.model.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class PaymentResponse {
    private Long id;
    private PaymentStatus status;
    private int totalAmount;
    private String message;

    public PaymentResponse(Long id, PaymentStatus status, int totalAmount, String message) {
        this.id = id;
        this.status = status;
        this.totalAmount = totalAmount;
        this.message = message;
    }
}
