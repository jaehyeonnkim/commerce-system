package kr.hhplus.be.server.domain.product.service;

import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.repository.ProductReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProductReader {

    private final ProductReaderRepository productReaderRepository;

    //의존성 주입
    @Autowired
    public ProductReader(ProductReaderRepository productReaderRepository) {
        this.productReaderRepository = productReaderRepository;
    }


    public Page<Product> getProducts(Pageable pageable) {
        return productReaderRepository.findAll(pageable);
    }
}
