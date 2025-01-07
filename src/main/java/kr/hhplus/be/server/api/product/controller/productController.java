package kr.hhplus.be.server.api.product.controller;

import kr.hhplus.be.server.api.product.dto.ProductRequest;
import kr.hhplus.be.server.api.product.dto.ProductResponse;
import kr.hhplus.be.server.common.CommonConstants;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.service.ProductReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class productController {

    private final ProductReader productReader;

    public productController(ProductReader productReader) {
        this.productReader = productReader;
    }

    //상품 조회
    @GetMapping
    public Page<ProductResponse> getProducts(@RequestParam(defaultValue = CommonConstants.DEFAULT_PAGE_NO) int pageNo,
                                            @RequestParam(defaultValue = CommonConstants.DEFAULT_SORT_CRITERIA) String criteria) {
        Pageable pageable = PageRequest.of(pageNo, CommonConstants.PAGESIZE, Sort.by(Sort.Direction.DESC, criteria));

        Page<Product> products = productReader.getProducts(pageable);

        return products.map(ProductResponse::from);
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
