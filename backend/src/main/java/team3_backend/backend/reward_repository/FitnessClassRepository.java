package team3_backend.backend.reward_repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team3_backend.backend.models_reward.FitnessClass;

@Repository
public interface FitnessClassRepository extends JpaRepository<FitnessClass, Integer> {
    // to find a class by its type
    Optional<FitnessClass> findByType(String type);

    // You can define custom query methods here if needed.
    
}
