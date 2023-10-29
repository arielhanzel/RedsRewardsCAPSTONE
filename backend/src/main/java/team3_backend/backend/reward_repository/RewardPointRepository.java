package team3_backend.backend.reward_repository;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.RewardPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardPointRepository extends JpaRepository<RewardPoint, Integer> {
    
    // Find all reward points entries for a specific user:
    List<RewardPoint> findByApplicationUser(ApplicationUser applicationUser);
    
    // Find the sum of reward points for a specific user:
    @Query("SELECT SUM(rp.pointBalance) FROM RewardPoint rp WHERE rp.applicationUser = :applicationUser")
    Optional<Integer> findTotalPointsByApplicationUser(ApplicationUser applicationUser);
}
