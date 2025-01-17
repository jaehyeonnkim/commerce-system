package kr.hhplus.be.server.infra.product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl  {
//
//    private ProductRepository productRepository;
//
//    @PersistenceContext
//    private final EntityManager em;
//
//    @Override
//    public Product findById(Long productId) {
//        return em.find(Product.class, productId);
//    }
//
//    @Override
//    public Page<Product> findAll(Pageable pageable) {
//        return productRepository.findAll(pageable);
//    }
}

