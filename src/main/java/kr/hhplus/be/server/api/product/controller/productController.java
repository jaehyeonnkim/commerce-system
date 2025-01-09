package kr.hhplus.be.server.api.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.api.product.dto.ProductResponse;
import kr.hhplus.be.server.common.CommonConstants;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Tag(name = "상품 API", description = "상품을 관리하는 API")
public class productController {

    private final ProductService productService;

    //상품 조회
    @Operation(summary = "상품 조회")
    @GetMapping
    public Page<ProductResponse> getProducts(@RequestParam(defaultValue = CommonConstants.DEFAULT_PAGE_NO) int pageNo,
                                            @RequestParam(defaultValue = CommonConstants.DEFAULT_SORT_CRITERIA) String criteria) {
        Pageable pageable = PageRequest.of(pageNo, CommonConstants.PAGESIZE, Sort.by(Sort.Direction.DESC, criteria));

        Page<Product> products = productService.getProducts(pageable);

        return products.map(ProductResponse::from);
    }

    //인기 상품 조회
    @Operation(summary = "인기 상품 조회")
    @GetMapping("/popular")
    public List<?> getPopularProducts() {
        return productService.getPopularProducts();
    }

}
