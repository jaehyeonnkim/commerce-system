package kr.hhplus.be.server.application.product.dto.response;

import kr.hhplus.be.server.domain.product.dto.ProductResponse;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFacadeResponse {
    private Long id;
    private String name;
    private int price;
    private int stock;

    public static ProductFacadeResponse toResponse(ProductResponse result){
        return ProductFacadeResponse.builder()
                .id(result.getId())
                .name(result.getName())
                .price(result.getPrice())
                .stock(result.getStock())
                .build();
    }
}
