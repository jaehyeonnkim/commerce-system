package kr.hhplus.be.server.api.order.dto;

import kr.hhplus.be.server.application.order.dto.response.OrderFacadeResponse;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class OrderResult {
    private Long orderId;
    private int totalAmount;


    public static OrderResult toResponse(OrderFacadeResponse orderFacadeResponse) {
        return OrderResult.builder()
                .orderId(orderFacadeResponse.getOrderId())
                .totalAmount(orderFacadeResponse.getTotalAmount())
                .build();
    }
}
