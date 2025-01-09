package kr.hhplus.be.server.api.order.dto;

import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
public class OrderRequest {
    private Long userId;
    private Long productId;
    private int quantity;

    public OrderRequest(Long userId, Long productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
