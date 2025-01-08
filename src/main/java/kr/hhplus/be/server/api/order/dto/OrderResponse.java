package kr.hhplus.be.server.api.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class OrderResponse {
    private Long id;
    private String message;
}
