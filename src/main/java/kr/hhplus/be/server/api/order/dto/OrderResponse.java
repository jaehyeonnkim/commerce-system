package kr.hhplus.be.server.api.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String message;
    private int totalAmount;

    public OrderResponse(Long id, int totalAmount) {
        this.id = id;
        this.totalAmount = totalAmount;
    }

}
