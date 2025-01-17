package kr.hhplus.be.server.domain.product.dto;

import kr.hhplus.be.server.domain.product.model.Product;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private int price;
    private int stock;

    public static ProductResponse toResult(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
