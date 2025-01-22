package kr.hhplus.be.server.domain.product.service;

import kr.hhplus.be.server.domain.order.model.OrderProduct;
import kr.hhplus.be.server.domain.order.repository.OrderProductJpaRepository;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.product.dto.ProductResponse;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class )
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;


    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderProductJpaRepository orderProductJpaRepository;

    @Test
    @DisplayName("상품 조회 성공")
    public void 상품_조회_성공() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> productList = Arrays.asList(
                new Product(1L, "멋진가방", 10000, 100, 0, LocalDateTime.now(), LocalDateTime.now()),
                new Product(2L, "멋진신발", 20000, 100, 0, LocalDateTime.now(), LocalDateTime.now()));

        Page<Product> productPage = new PageImpl<>(productList);

        when(productRepository.findAll(pageable)).thenReturn(productPage);

        // when
        Page<ProductResponse> result = productService.getProducts(pageable);

        // then
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("멋진가방", result.getContent().get(0).getName());
    }

    @Test
    @DisplayName("인기 상품 조회 성공")
    public void 인기_상품_조회_성공() {
        // given
        List<OrderProduct> popularProducts = Arrays.asList(
                OrderProduct.createOrderItem(new Product(1L, "멋진가방", 10000, 100, 0, LocalDateTime.now(), LocalDateTime.now())
                        ,1000,10),
                OrderProduct.createOrderItem(new Product(2L, "멋진신발", 20000, 100, 0, LocalDateTime.now(), LocalDateTime.now())
                        ,1000,8)
        );

        when(orderProductJpaRepository.findPopularProducts()).thenReturn(popularProducts);

        // when
        List<OrderProduct> result = orderService.getPopularProducts();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(10, result.get(0).getQuantity());
    }

}