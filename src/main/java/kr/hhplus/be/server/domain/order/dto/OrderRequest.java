package kr.hhplus.be.server.domain.order.dto;

import kr.hhplus.be.server.application.order.dto.request.OrderFacadeRequest;
import kr.hhplus.be.server.domain.user.model.User;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long productId;
    private int quantity;
    private Long userId;

    public OrderFacadeRequest toOrderFacadeRequest(){
        return OrderFacadeRequest.builder()
                .productId(productId)
                .quantity(quantity)
                .userId(userId)
                .build();
    }
}