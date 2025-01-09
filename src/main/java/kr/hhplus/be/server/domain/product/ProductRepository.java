package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.api.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {
    Product findById(Long productId);
    Page<Product> findAll(Pageable pageable);

}
