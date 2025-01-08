package kr.hhplus.be.server.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {
    Product findById(Long productId);
    Page<Product> findAll(Pageable pageable);
}
