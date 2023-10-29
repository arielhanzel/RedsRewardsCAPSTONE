package team3_backend.backend.reward_repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.CheckIn;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    // You can define custom query methods here if needed.

    // to find all check-ins for a specific user
    List<CheckIn> findByApplicationUser(ApplicationUser user);
}