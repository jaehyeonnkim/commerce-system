package kr.hhplus.be.server.domain.wallet.model;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="wallet_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Enumerated(EnumType.STRING)
    @Comment("거래 타입")
    private TransactionType type;

    @Comment("거래 금액")
    private int amount;

    @Comment("잔액")
    private int balance;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
