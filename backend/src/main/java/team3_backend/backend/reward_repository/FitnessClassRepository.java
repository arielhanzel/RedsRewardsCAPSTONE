package team3_backend.backend.reward_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.FitnessClass;
import team3_backend.backend.models_reward.RewardRedemption;

@Repository
public interface FitnessClassRepository extends JpaRepository<FitnessClass, Integer> {
    // to find a class by its type
    Optional<FitnessClass> findByType(String type);
    // You can define custom query methods here if needed.
    
}
