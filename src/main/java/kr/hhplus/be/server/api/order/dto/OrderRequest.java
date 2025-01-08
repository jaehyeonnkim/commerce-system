package kr.hhplus.be.server.api.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class OrderRequest {
    private Long userId;
    private Long productId;
    private int quantity;
}
