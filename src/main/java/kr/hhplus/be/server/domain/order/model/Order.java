package kr.hhplus.be.server.domain.order.model;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.domain.coupon.model.CouponStatus;
import kr.hhplus.be.server.domain.payment.model.Payment;
import kr.hhplus.be.server.domain.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name="orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    private String status;

    private LocalDateTime orderedAt;

    private int totalAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderItems.add(orderProduct);
        orderProduct.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(User user, OrderProduct orderproduct) {
        Order order = new Order();
        order.setUser(user);
        order.addOrderProduct(orderproduct);
        order.setStatus("ORDER");
        order.setTotalAmount(orderproduct.getTotalPrice());
        order.setOrderedAt(LocalDateTime.now());
        return order;
    }

    public void markAsPaid() {
        validateActive();
        this.status = "PAID";
        this.updatedAt = LocalDateTime.now();
    }

    //주문 정보 여부 검증
    public void validateActive() {
        if (!"ORDER".equals(this.status)) {
            throw new BusinessException(ErrorCode.INVALID_ORDER.getCode(), ErrorCode.INVALID_ORDER.getMessage());
        }
    }
}
