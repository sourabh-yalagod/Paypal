package com.paypal.wallet_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paypal.wallet_service.lib.HoldStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet_holds")
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
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private HoldStatus status;

    @Column(nullable = false)
    private String holdReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private WalletEntity wallet;

    public void capture() {
        this.status = HoldStatus.CAPTURED;
    }

    public void release() {
        this.status = HoldStatus.RELEASED;
    }
}
