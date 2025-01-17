package kr.hhplus.be.server.application.product.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopProductResponse {

    private List<ProductItem> productList;

    public static TopProductResponse toResponse(List<ProductItem> productList) {
        return TopProductResponse.builder()
                .productList(productList)
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductItem {
        private Long productId;
        private String name;
        private int totalQuantity;
    }
}
