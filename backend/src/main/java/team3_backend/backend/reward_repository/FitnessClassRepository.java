package team3_backend.backend.reward_repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team3_backend.backend.models_reward.FitnessClass;

@Repository
public interface FitnessClassRepository extends JpaRepository<FitnessClass, Integer> {
    // You can define custom query methods here if needed.

    // to find a class by its type
    List<FitnessClass> findByType(String type);
}
