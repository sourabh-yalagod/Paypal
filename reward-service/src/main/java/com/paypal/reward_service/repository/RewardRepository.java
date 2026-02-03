package com.paypal.reward_service.repository;

import com.paypal.reward_service.entity.RewardEntity;
import com.paypal.reward_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RewardRepository extends JpaRepository<RewardEntity,String> {
    public Optional<UserEntity> getRewardByUserId(String userId);
}
