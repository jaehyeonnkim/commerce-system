package kr.hhplus.be.server.domain.product.repository;

import kr.hhplus.be.server.domain.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReaderRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);
}
