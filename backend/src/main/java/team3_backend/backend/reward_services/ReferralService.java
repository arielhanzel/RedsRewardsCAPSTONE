package team3_backend.backend.reward_services;

import java.util.List;

import org.springframework.stereotype.Service;
import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.Referral;
import team3_backend.backend.models_reward_dto.ReferralDTO;
import team3_backend.backend.reward_repository.ReferralRepository;
import team3_backend.backend.repository.UserRepository;

@Service
public class ReferralService {
    private final ReferralRepository referralRepository;
    private final UserRepository userRepository;

    public ReferralService(ReferralRepository referralRepository, UserRepository userRepository) {
        this.referralRepository = referralRepository;
        this.userRepository = userRepository;
    }

    public ReferralDTO registerReferral(Integer referrerId, Integer refereeId) {
        ApplicationUser referrer = userRepository.findById(referrerId)
                .orElseThrow(() -> new RuntimeException("Referrer not found"));
        ApplicationUser referee = userRepository.findById(refereeId)
                .orElseThrow(() -> new RuntimeException("Referee not found"));

        Referral referral = new Referral();
        referral.setReferrer(referrer);
        referral.setReferee(referee);
        referral = referralRepository.save(referral);

        return convertToDTO(referral);
    }

    private ReferralDTO convertToDTO(Referral referral) {
        return new ReferralDTO(
                referral.getReferralId(),
                referral.getReferrer().getUsername(),
                referral.getReferee().getUsername()
        );
    }

    public ReferralDTO viewReferrer(String username) {
        return null;
    }

    public List<ReferralDTO> viewReferree(String username) {
        return null;
    }

    public List<ReferralDTO> viewReferrals() {
        return null;
    }
}
