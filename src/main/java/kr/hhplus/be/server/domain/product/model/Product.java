package kr.hhplus.be.server.domain.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    private Integer saleCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Product(Long id, String name, Integer price, Integer stock, Integer saleCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.saleCount = saleCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Product() {

    }
}
