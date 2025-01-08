package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.NotEnoughStockException;
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


    public Product() {}

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stock += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stock - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 부족합니다");}
        this.stock = restStock;
    }
}
