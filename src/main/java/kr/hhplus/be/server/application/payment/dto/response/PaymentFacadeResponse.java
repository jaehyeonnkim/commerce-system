package kr.hhplus.be.server.application.payment.dto.response;

import kr.hhplus.be.server.domain.payment.dto.PaymentResponse;
import kr.hhplus.be.server.domain.payment.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
public class PaymentFacadeResponse {
    private Long paymentId;
    private PaymentStatus status;
    private int totalAmount;

    public static PaymentFacadeResponse toResponse(PaymentResponse response) {
        return PaymentFacadeResponse.builder()
                .status(response.getStatus())
                .totalAmount(response.getTotalAmount())
                .build();
    }
}
