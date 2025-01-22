package kr.hhplus.be.server.domain.payment.model;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.coupon.model.Coupon;
import kr.hhplus.be.server.domain.coupon.model.CouponType;
import kr.hhplus.be.server.domain.order.model.Order;
import kr.hhplus.be.server.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private int totalAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    //연관관계
    public void setUser(User user) {
        this.user = user;
        user.getPayments().add(this);
    }

    public void setOrder(Order order) {
        this.order = order;
        order.setPayment(this);
    }
    //==생성 메서드==//
    public static Payment createPayment(User user, Order order, Coupon coupon, PaymentType type) {
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setOrder(order);
        payment.setCoupon(coupon);
        payment.setType(type);
        payment.setCreatedAt(LocalDateTime.now());
        payment.calculateTotalAmount();
        payment.setStatus(PaymentStatus.PAID);
        payment.setUpdatedAt(LocalDateTime.now());
        return payment;
    }

    //금액 계산
    public void calculateTotalAmount() {
        int orderAmount = order.getTotalAmount();
        int discount = 0;

        if (coupon != null) {
            discount = coupon.getType() == CouponType.FIXED
                    ? coupon.getValue()
                    : (int) (orderAmount * (coupon.getValue() / 100.0));
        }

        this.totalAmount = orderAmount - discount;

        if (this.totalAmount < 0) {
            this.totalAmount = 0;
        }
    }

    public void markAsPaid() {
        this.status = PaymentStatus.PAID;
        this.updatedAt = LocalDateTime.now();
    }
}
