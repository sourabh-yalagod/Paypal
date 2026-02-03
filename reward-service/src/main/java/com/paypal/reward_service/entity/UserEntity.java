package com.paypal.reward_service.entity;

import com.paypal.reward_service.lib.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.USER;

    private String password;
}
