package kr.hhplus.be.server.api.product.dto;

import kr.hhplus.be.server.domain.product.model.Product;
import lombok.*;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private int price;

    public static ProductResponse from(Product product) {
        ProductResponse response = new ProductResponse();
        response.id = product.getId();
        response.name = product.getName();
        response.price = product.getPrice();
        return response;
    }
}
