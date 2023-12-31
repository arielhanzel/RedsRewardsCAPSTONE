package team3_backend.backend.reward_services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.RewardPoint;
import team3_backend.backend.models_reward.UnapprovedReward;
import team3_backend.backend.models_reward_dto.RewardPointDTO;
import team3_backend.backend.models_reward_dto.UnapprovedRewardDTO;
import team3_backend.backend.repository.UserRepository;
import team3_backend.backend.reward_repository.RewardPointRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardPointService {

    private final RewardPointRepository rewardPointRepository;
    private final UserRepository userRepository;

    public RewardPointService(RewardPointRepository rewardPointRepository, UserRepository userRepository ) {
        this.rewardPointRepository = rewardPointRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public RewardPointDTO addRewardPoints(ApplicationUser user, int points) {
        RewardPoint rewardPoint = new RewardPoint();
        rewardPoint.setApplicationUser(user);
        rewardPoint.setTimestamp(LocalDateTime.now());
        rewardPoint.setPointBalance(points);

        RewardPoint savedRewardPoint = rewardPointRepository.save(rewardPoint);
        
        return convertToRewardPointDTO(savedRewardPoint);
    }

    public List<RewardPointDTO> getAllRewardPointsForUser(ApplicationUser user) {
        List<RewardPoint> rewardPoints = rewardPointRepository.findByApplicationUser(user);
        return rewardPoints.stream()
                           .map(this::convertToRewardPointDTO)
                           .collect(Collectors.toList());
    }

    public Integer getTotalRewardPointsForUser(ApplicationUser user) {
        return rewardPointRepository.findTotalPointsByApplicationUser(user)
                                    .orElse(0); // Return 0 if the user has no reward points
    }

    private RewardPointDTO convertToRewardPointDTO(RewardPoint rewardPoint) {
        return new RewardPointDTO(
                rewardPoint.getPointId(),
                rewardPoint.getApplicationUser().getUsername(),
                rewardPoint.getTimestamp(),
                rewardPoint.getPointBalance()
        );
    }

    public RewardPointDTO addPointsClassAttendance(Integer userId) {
        ApplicationUser applicationUser = userRepository.findById(userId).get();
        return addRewardPoints(applicationUser, 10);
    }

    public RewardPointDTO addPointsReferrer(String username) {
        ApplicationUser applicationUser = userRepository.findByUsername(username).get();
        return addRewardPoints(applicationUser,100);
    }

    public List<RewardPointDTO> getAllRewardPoints() {
       List<RewardPoint> rewardPoints = rewardPointRepository.findAll();
    return rewardPoints.stream()
                        .map(this::convertToRewardPointDTO)
                        .collect(Collectors.toList());
    }
    
    // Other service methods can be added here as needed
}