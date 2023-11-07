package team3_backend.backend.reward_services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.Referral;
import team3_backend.backend.models_reward_dto.ReferralDTO;
import team3_backend.backend.models_reward_dto.RewardPointDTO;
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
        ApplicationUser referrer = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Referrer not found"));

        // Assuming that there's a method to find referrals by the referrer in the ReferralRepository
        List<Referral> referrals = referralRepository.findByReferrer(referrer);
        // This will return the latest referral made by the referrer
        return referrals.stream()
                .map(this::convertToDTO)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Referral not found"));
    }

    public List<ReferralDTO> viewReferree(String username) {
        ApplicationUser referee = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Referee not found"));

        List<Referral> referrals = referralRepository.findByReferee(referee);
        return referrals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
         
    }
    
    public List<ReferralDTO> viewReferrals() {
        List<Referral> referrals = referralRepository.findAll();
        return referrals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
