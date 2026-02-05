package com.paypal.wallet_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wallets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal availableBalance = BigDecimal.ZERO;

    @Version
    private Long version;

    @OneToMany(
            mappedBy = "wallet",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<WalletHoldEntity> walletHolds = new ArrayList<>();


    // Business Logic
    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
        availableBalance = availableBalance.add(amount);
    }

    public void debit(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public void reduceAvailable(BigDecimal amount) {
        availableBalance = availableBalance.subtract(amount);
    }

    public void increaseAvailable(BigDecimal amount) {
        availableBalance = availableBalance.add(amount);
    }

    public boolean hasSufficientBalance(BigDecimal amount) {
        return availableBalance.compareTo(amount) >= 0;
    }
}
