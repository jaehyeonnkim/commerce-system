package kr.hhplus.be.server.application.order.dto.response;

import kr.hhplus.be.server.domain.order.dto.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
public class OrderFacadeResponse {
    private Long orderId;
    private int totalAmount;

    public static OrderFacadeResponse toResponse(OrderResponse orderResponse) {
        return OrderFacadeResponse.builder()
                .orderId(orderResponse.getOrderId())
                .totalAmount(orderResponse.getTotalAmount())
                .build();
    }
}
