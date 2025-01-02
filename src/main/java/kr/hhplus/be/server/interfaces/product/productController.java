package kr.hhplus.be.server.interfaces.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class productController {

    //상품 조회
    @GetMapping
    public ResponseEntity<Object> getProducts(@RequestBody ProductRequest productRequest) {

        Map<String, Object> response = Map.of(
                "page", 1,
                "size", 10,
                "sort", "price,desc",
                "totalCount", 654,
                "productList", List.of(
                        Map.of(
                                "productId", 23421,
                                "name", "가방1",
                                "price", 43000,
                                "categoryName", "bag",
                                "count", 300,
                                "createdAt", "2024-03-02T03:10:00"
                        ),
                        Map.of(
                                "productId", 5421,
                                "name", "가방2",
                                "price", 41000,
                                "categoryName", "bag",
                                "count", 112,
                                "createdAt", "2024-04-02T08:20:00"
                        )
                )
        );

        return ResponseEntity.ok(response);
    }

    //인기 상품 조회
    @GetMapping("/popular")
    public ResponseEntity<Object> getPopularProducts(@RequestBody ProductRequest productRequest) {
        Map<String, Object> response = Map.of(
                "productList", List.of(
                        Map.of(
                                "itemId", 2341,
                                "totalQuantity", 93,
                                "calculatedAt", "2025-01-03T00:00:00Z"
                        ),
                        Map.of(
                                "itemId", 2341,
                                "totalQuantity", 93,
                                "calculatedAt", "2025-01-03T00:00:00Z"
                        ),
                        Map.of(
                                "itemId", 2341,
                                "totalQuantity", 93,
                                "calculatedAt", "2025-01-03T00:00:00Z"
                        )
                )
        );
        return ResponseEntity.ok(response);
    }

}
