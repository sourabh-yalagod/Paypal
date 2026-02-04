package com.paypal.wallet_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wallets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WalletHoldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            nullable = false,
            name = "wallet_id",
            foreignKey = @ForeignKey(name = "fk_wallet_hold_wallet"))
    private WalletEntity wallet;
}
