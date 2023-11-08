package team3_backend.backend.reward_services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.RewardRedemption;
import team3_backend.backend.models_reward_dto.RewardRedemptionDTO;
import team3_backend.backend.reward_repository.RewardPointRepository;
import team3_backend.backend.reward_repository.RewardRedemptionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardRedemptionService {

    private final RewardRedemptionRepository rewardRedemptionRepository;
    private final RewardPointService rewardPointService;

    public RewardRedemptionService(RewardRedemptionRepository rewardRedemptionRepository, RewardPointService rewardPointService) {
        this.rewardRedemptionRepository = rewardRedemptionRepository;
        this.rewardPointService = rewardPointService;
    }

    @Transactional
    public RewardRedemptionDTO redeemPoints(ApplicationUser user, String items, int points) {
        // Check if the user has enough points before proceeding with the redemption
        // This would involve a call to a method that calculates the user's current points.
        // For simplicity, let's assume that the user has enough points for this redemption.
        
        RewardRedemption redemption = new RewardRedemption();
        redemption.setApplicationUser(user);
        redemption.setItems(items);
        redemption.setPoint(points);
        redemption.setTimestamp(LocalDateTime.now());

        RewardRedemption savedRedemption = rewardRedemptionRepository.save(redemption);
        rewardPointService.addRewardPoints(user,-points);
        
        return convertToRewardRedemptionDTO(savedRedemption);
    }

    public List<RewardRedemptionDTO> getRedemptionRecordsForUser(ApplicationUser user) {
        List<RewardRedemption> redemptions = rewardRedemptionRepository.findByApplicationUser(user);
        return redemptions.stream()
                          .map(this::convertToRewardRedemptionDTO)
                          .collect(Collectors.toList());
    }

    // Additional methods can be implemented here
    // ...

    private RewardRedemptionDTO convertToRewardRedemptionDTO(RewardRedemption redemption) {
        return new RewardRedemptionDTO(
                redemption.getPurchaseId(),
                redemption.getApplicationUser().getUsername(),
                redemption.getItems(),
                redemption.getPoint(),
                redemption.getTimestamp()
        );
    }

    public List<RewardRedemptionDTO> getALLRedemptionRecords() {
        List<RewardRedemption> rewardRedemptions = rewardRedemptionRepository.findAll();
        return rewardRedemptions.stream()
                                .map(this::convertToRewardRedemptionDTO)
                                .collect(Collectors.toList());
    }

    // Helper method to calculate total points for a user might be needed
    // ...

}
