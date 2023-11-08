package team3_backend.backend.reward_services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward_dto.RewardPointDTO;
import team3_backend.backend.repository.UserRepository;
import team3_backend.backend.reward_repository.UnapprovedRewardRepository;

@Service
public class RewardApprovalService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RewardPointService rewardPointService;

    @Autowired
    UnapprovedRewardRepository unapprovedRewardRepository;


    public RewardPointDTO approveRewardPoint(Integer pointId, String username, Integer points) {
        ApplicationUser applicationUser = userRepository.findByUsername(username).get();
        //add point to RewardPoint Table
        RewardPointDTO savedapproval = rewardPointService.addRewardPoints(applicationUser,points);

        //Delete row in Unapproved Reward table
        unapprovedRewardRepository.deleteById(pointId);
        return savedapproval;
    }

    
}
