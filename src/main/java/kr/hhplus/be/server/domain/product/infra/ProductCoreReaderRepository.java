package kr.hhplus.be.server.domain.product.infra;

import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.repository.ProductReaderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCoreReaderRepository  {

    private ProductReaderRepository productReaderRepository;

    public Page<Product> findAll(Pageable pageable) {
        return productReaderRepository.findAll(pageable);
    }
}

