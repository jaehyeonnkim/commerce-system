package kr.hhplus.be.server.domain.product.service;

import kr.hhplus.be.server.domain.order.repository.OrderProductJpaRepository;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final  OrderProductJpaRepository orderProductJpaRepository;

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Map<String, Object>> getPopularProducts() {
        List<Object[]> result = orderProductJpaRepository.findPopularProducts();
        List<Map<String, Object>> products = new ArrayList<>();
        for (Object[] row : result) {
            Map<String, Object> productData = new HashMap<>();
            productData.put("productId", ((Number) row[0]).longValue());
            productData.put("totalQuantity", ((Number) row[1]).intValue());
            products.add(productData);
        }
        return products;
    }
}
