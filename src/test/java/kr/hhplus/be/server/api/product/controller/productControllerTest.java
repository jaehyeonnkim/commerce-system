package kr.hhplus.be.server.api.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.api.product.dto.ProductResponse;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class productControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void 상품_조회_통합테스트() throws Exception {
        // given
        Product product = new Product(1L, "멋진가방", 10000, 100, 0, LocalDateTime.now(), LocalDateTime.now());
        Page<Product> productPage = new PageImpl<>(List.of(product), PageRequest.of(0, 10, Sort.by("name")), 1);
        when(productService.getProducts(any(PageRequest.class))).thenReturn(productPage);

        // when & then
        MvcResult result = mockMvc.perform(get("/api/product")
                        .param("pageNo", "0")
                        .param("criteria", "name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("상품1")))
                .andExpect(jsonPath("$.content[0].price", is(10000)))
                .andReturn();

        System.out.println("GET - /api/product " + result.getResponse().getStatus() + " OK");
    }

    @Test
    public void 인기_상품_조회_통합테스트() throws Exception {
        // given
        List<?> popularProducts = Arrays.asList(
                new ProductResponse(1L, "상품1", 10000),
                new ProductResponse(2L, "상품2", 20000)
        );
        when(productService.getPopularProducts()).thenReturn((List<Map<String, Object>>) popularProducts);

        // when & then
        MvcResult result = mockMvc.perform(get("/api/product/popular")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("상품1")))
                .andExpect(jsonPath("$[0].price", is(10000)))
                .andReturn();

        System.out.println("GET - /api/product/popular " + result.getResponse().getStatus() + " OK");
    }
}