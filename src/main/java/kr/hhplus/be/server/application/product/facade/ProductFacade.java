package kr.hhplus.be.server.application.product.facade;

import kr.hhplus.be.server.application.coupon.dto.response.UserCouponResponse;
import kr.hhplus.be.server.application.product.dto.response.TopProductResponse;
import kr.hhplus.be.server.domain.coupon.dto.CouponResponse;
import kr.hhplus.be.server.domain.coupon.sersvice.CouponService;
import kr.hhplus.be.server.domain.order.model.OrderProduct;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.product.dto.ProductResponse;
import kr.hhplus.be.server.domain.product.service.ProductService;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;
    private final CouponService couponService;
    private final UserService userService;
    private final OrderService orderService;

    @Transactional
    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @Transactional
    public TopProductResponse getPopularProducts() {
        List<OrderProduct> popularProducts = orderService.getPopularProducts();

        List<TopProductResponse.ProductItem> productList = popularProducts.stream()
                .map(product -> TopProductResponse.ProductItem.builder()
                        .productId(product.getProduct().getId())
                        .name(product.getProduct().getName())
                        .totalQuantity(product.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return TopProductResponse.toResponse(productList);
    }

}
