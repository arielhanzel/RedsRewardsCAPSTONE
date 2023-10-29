package team3_backend.backend.reward_repository;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.RewardRedemption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRedemptionRepository extends JpaRepository<RewardRedemption, Integer> {
    
    // For example, to find all reward redemptions for a specific user:
    List<RewardRedemption> findByApplicationUser(ApplicationUser applicationUser);
    
    // ... Any other custom query methods you might need.
}