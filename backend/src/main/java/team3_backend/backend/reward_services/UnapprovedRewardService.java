package team3_backend.backend.reward_services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.UnapprovedReward;
import team3_backend.backend.models_reward_dto.UnapprovedRewardDTO;
import team3_backend.backend.reward_repository.UnapprovedRewardRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnapprovedRewardService {

    private final UnapprovedRewardRepository unapprovedRewardRepository;

    public UnapprovedRewardService(UnapprovedRewardRepository unapprovedRewardRepository) {
        this.unapprovedRewardRepository = unapprovedRewardRepository;
    }

    @Transactional
    public UnapprovedRewardDTO addUnapprovedReward(ApplicationUser user, int points) {
        UnapprovedReward unapprovedReward = new UnapprovedReward();
        unapprovedReward.setApplicationUser(user);
        unapprovedReward.setTimestamp(LocalDateTime.now());
        unapprovedReward.setPointBalance(points);

        UnapprovedReward savedUnapprovedReward = unapprovedRewardRepository.save(unapprovedReward);
        
        return convertToUnapprovedRewardDTO(savedUnapprovedReward);
    }

    public List<UnapprovedRewardDTO> getAllUnapprovedRewardsForUser(ApplicationUser user) {
        List<UnapprovedReward> unapprovedRewards = unapprovedRewardRepository.findByApplicationUser(user);
        return unapprovedRewards.stream()
                                .map(this::convertToUnapprovedRewardDTO)
                                .collect(Collectors.toList());
    }

    // This method could be used later to approve unapproved rewards

    private UnapprovedRewardDTO convertToUnapprovedRewardDTO(UnapprovedReward unapprovedReward) {
        return new UnapprovedRewardDTO(
                unapprovedReward.getPointId(),
                unapprovedReward.getApplicationUser().getUsername(),
                unapprovedReward.getTimestamp(),
                unapprovedReward.getPointBalance()
        );
    }

   
    public List<UnapprovedRewardDTO> getAllUnapprovedRewards() {
       List<UnapprovedReward> unapprovedRewards = unapprovedRewardRepository.findAll();
    return unapprovedRewards.stream()
                            .map(this::convertToUnapprovedRewardDTO)
                            .collect(Collectors.toList());
    }

    // Other service methods can be added here as needed
}