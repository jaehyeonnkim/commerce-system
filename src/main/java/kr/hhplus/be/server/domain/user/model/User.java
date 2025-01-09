package kr.hhplus.be.server.domain.user.model;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.coupon.model.IssueCoupon;
import kr.hhplus.be.server.domain.order.model.Order;
import kr.hhplus.be.server.domain.payment.model.Payment;
import kr.hhplus.be.server.domain.wallet.model.Wallet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name="users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)//order table에 있는 user
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<IssueCoupon> issueCoupons = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Wallet> wallets = new ArrayList<>();

    private String name;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    //validation
    public void validateUser() {
        if (this == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
    }
}
