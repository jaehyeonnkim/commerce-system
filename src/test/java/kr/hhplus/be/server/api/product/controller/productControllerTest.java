package kr.hhplus.be.server.api.product.controller;

import kr.hhplus.be.server.domain.order.model.OrderProduct;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.product.dto.ProductResponse;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ProductControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(ProductControllerTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private OrderService orderService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void 상품_조회_통합테스트() throws Exception {
        // given
        Product product1 = Product.builder().name("멋진가방").price(10000).stock(100).build();
        Product product2 = Product.builder().name("멋진신발").price(20000).stock(100).build();
        Page<ProductResponse> products = new PageImpl<>(
                Arrays.asList(
                        ProductResponse.builder()
                                .id(product1.getId())
                                .name(product1.getName())
                                .price(product1.getPrice())
                                .stock(product1.getStock())
                                .build(),
                        ProductResponse.builder()
                                .id(product2.getId())
                                .name(product2.getName())
                                .price(product2.getPrice())
                                .stock(product2.getStock())
                                .build()
                )
        );

        // Mocking service layer
        when(productService.getProducts(any(Pageable.class))).thenReturn(products);

        // when & then
        MvcResult result = mockMvc.perform(get("/api/product")
                        .param("pageNo", "0")
                        .param("criteria", "name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("멋진가방")))
                .andExpect(jsonPath("$.content[0].price", is(10000)))
                .andReturn();

        logger.info("GET - /api/product Status: {}", result.getResponse().getStatus());
    }

    @Test
    @Disabled
    public void 인기_상품_조회_통합테스트() throws Exception {
        // given
        Product product1 = Product.builder().name("멋진가방").price(10000).stock(100).build();
        Product product2 = Product.builder().name("멋진신발").price(20000).stock(100).build();
        List<OrderProduct> popularProducts = Arrays.asList(
                OrderProduct.createOrderItem(product1,1000,10),
                OrderProduct.createOrderItem(product2,1000,8)
                );
        when(orderService.getPopularProducts()).thenReturn(popularProducts);

        // when & then
        MvcResult result = mockMvc.perform(get("/api/product/popular")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].product.name", is("멋진가방")))
                .andExpect(jsonPath("$[0].quantity", is(10)))
                .andExpect(jsonPath("$[1].product.name", is("멋진신발")))
                .andExpect(jsonPath("$[1].quantity", is(8)))
                .andReturn();

        logger.info("GET - /api/product/popular Status: {}", result.getResponse().getStatus());
    }
}