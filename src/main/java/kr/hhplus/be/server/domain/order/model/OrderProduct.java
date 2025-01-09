package kr.hhplus.be.server.domain.order.model;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.product.model.Product;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_produet_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int orderPrice; //주문 가격
    private int quantity; //주문 수량

    private LocalDateTime orderedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    //==생성 메서드==//
    public static OrderProduct createOrderItem(Product Product, int orderPrice, int quantity) {
        OrderProduct OrderProduct = new OrderProduct();
        OrderProduct.setProduct(Product);
        OrderProduct.setOrderPrice(orderPrice);
        OrderProduct.setQuantity(quantity);
        OrderProduct.setOrderedAt(LocalDateTime.now());

        Product.removeStock(quantity);
        return OrderProduct;
    }

    public int getTotalPrice() {
        return getOrderPrice() * getQuantity();
    }
}
