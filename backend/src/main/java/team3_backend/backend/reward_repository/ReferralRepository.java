package team3_backend.backend.reward_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.Referral;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Integer> {
    // You can define custom query methods here if needed.

    // to find all referrals made by a specific user (referrer)
    List<Referral> findByReferrer(ApplicationUser referrer);

    //to find a referrer for a specific referee
    List<Referral> findByReferee(ApplicationUser referee);

}
