package team3_backend.backend.reward_repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.CheckOut;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, Integer> {

    List<CheckOut> findByApplicationUser(ApplicationUser user);
    
    // Additional custom queries can be added here if needed

}