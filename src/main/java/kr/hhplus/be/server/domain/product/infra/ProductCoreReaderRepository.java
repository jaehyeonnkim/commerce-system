package kr.hhplus.be.server.domain.product.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.repository.ProductReaderRepository;
import kr.hhplus.be.server.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductCoreReaderRepository implements ProductReaderRepository {

    private ProductReaderRepository productReaderRepository;

    @PersistenceContext
    private final EntityManager em;

    public Product findById(Long productId) {
        return em.find(Product.class, productId);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productReaderRepository.findAll(pageable);
    }
}

