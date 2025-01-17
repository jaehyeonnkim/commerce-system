package kr.hhplus.be.server.api.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.product.dto.response.TopProductResponse;
import kr.hhplus.be.server.application.product.facade.ProductFacade;
import kr.hhplus.be.server.common.CommonConstants;
import kr.hhplus.be.server.common.dto.ApiResponse;
import kr.hhplus.be.server.domain.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Tag(name = "상품 API", description = "상품을 관리하는 API")
public class productController {

    private final ProductFacade productFacade;

    //상품 조회
    @Operation(summary = "상품 조회")
    @GetMapping
    public Page<ProductResponse> getProducts(@RequestParam(defaultValue = CommonConstants.DEFAULT_PAGE_NO) int pageNo,
                                            @RequestParam(defaultValue = CommonConstants.DEFAULT_SORT_CRITERIA) String criteria) {
        Pageable pageable = PageRequest.of(pageNo, CommonConstants.PAGESIZE, Sort.by(criteria));
        Page<ProductResponse> products = productFacade.getProducts(pageable);
        return ApiResponse.ok(products, LocalDateTime.now()).getData();
    }

    //인기 상품 조회
    @Operation(summary = "인기 상품 조회")
    @GetMapping("/popular")
    public ApiResponse<TopProductResponse> getPopularProducts() {
        TopProductResponse response = productFacade.getPopularProducts();
        return ApiResponse.ok(response, LocalDateTime.now());
    }

}
