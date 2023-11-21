package team3_backend.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.FitnessClass;
import team3_backend.backend.models_reward.RewardRedemption;
import team3_backend.backend.models_reward_dto.ApplicationUserDTO;
import team3_backend.backend.models_reward_dto.CheckInDTO;
import team3_backend.backend.models_reward_dto.CheckOutDTO;
import team3_backend.backend.models_reward_dto.ClassAttendanceDTO;
import team3_backend.backend.models_reward_dto.FitnessClassDTO;
import team3_backend.backend.models_reward_dto.ReferralDTO;
import team3_backend.backend.models_reward_dto.RewardPointDTO;
import team3_backend.backend.models_reward_dto.RewardRedemptionDTO;
import team3_backend.backend.models_reward_dto.UnapprovedRewardDTO;
import team3_backend.backend.repository.UserRepository;
import team3_backend.backend.reward_repository.FitnessClassRepository;
import team3_backend.backend.reward_repository.RewardRedemptionRepository;
import team3_backend.backend.reward_services.CheckInService;
import team3_backend.backend.reward_services.CheckOutService;
import team3_backend.backend.reward_services.ClassAttendanceService;
import team3_backend.backend.reward_services.FitnessClassService;
import team3_backend.backend.reward_services.ReferralService;
import team3_backend.backend.reward_services.RewardApprovalService;
import team3_backend.backend.reward_services.RewardPointService;
import team3_backend.backend.reward_services.RewardRedemptionService;
import team3_backend.backend.reward_services.UnapprovedRewardService;
import team3_backend.backend.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private CheckOutService checkOutService;

    @Autowired
    private FitnessClassService fitnessClassService;

    @Autowired
    private ClassAttendanceService classAttendanceService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FitnessClassRepository fitnessClassRepository;

    @Autowired
    private ReferralService referralService;

    @Autowired
    private RewardPointService rewardPointService;

    @Autowired
    private UnapprovedRewardService unapprovedRewardService;

    @Autowired
    private RewardApprovalService rewardApprovalService;

    @Autowired
    private RewardRedemptionService rewardRedemptionService;

    @Autowired RewardRedemptionRepository rewardRedemptionRepository;


    @GetMapping("/")
    public String userPage(){
        return "User information";
    }

    @PostMapping("/user/view")
    public ApplicationUserDTO userView(@RequestBody ApplicationUserDTO body){
        return userService.userView(body.getUsername());
    } 

   

    @PostMapping("/fitnessclass")
    public List<FitnessClassDTO> fitnessClass() {
        return fitnessClassService.getAllFitnessClasses();
    }

    @PostMapping("/registerclass")
    public ApplicationUserDTO registerClass(@RequestBody ApplicationUserDTO body){
        ApplicationUserDTO savedApplicationUserDTO = userService.registerClass(body.getUsername(),body.getClassType());
        ApplicationUser applicationUser = userRepository.findById(savedApplicationUserDTO.getUserId()).get();
        if(savedApplicationUserDTO != null){
            rewardPointService.addRewardPoints(applicationUser, 100);
        }
        return savedApplicationUserDTO;    
    }


    @PostMapping("/referrer/view")
    public ReferralDTO viewReferrer(@RequestBody ApplicationUserDTO body){
        return referralService.viewReferrer(body.getUsername());
    }

    @PostMapping("/referree/view")
    public List<ReferralDTO> viewReferree(@RequestBody ApplicationUserDTO body){
        return referralService.viewReferree(body.getUsername());
    }


    @PostMapping("/unapprovedreward/view")
    public List<UnapprovedRewardDTO> viewUnapprovedReward(@RequestBody ApplicationUserDTO body){
        ApplicationUser applicationUser = userRepository.findById(body.getUserId()).get();
        return unapprovedRewardService.getAllUnapprovedRewardsForUser(applicationUser);
    }


    @PostMapping("/rewardpoint/view")
    public List<RewardPointDTO> viewRewardPoint(@RequestBody ApplicationUserDTO body){
        ApplicationUser applicationUser = userRepository.findById(body.getUserId()).get();
        return rewardPointService.getAllRewardPointsForUser(applicationUser);
    }

    @PostMapping("/rewardpoint/totalpoints")
    public Integer viewTotalPoints(@RequestBody ApplicationUserDTO body){
        ApplicationUser applicationUser = userRepository.findByUsername(body.getUsername()).get();
        return rewardPointService.getTotalRewardPointsForUser(applicationUser);
    }

    @PostMapping("/rewardpoint/addpoints")
    public RewardPointDTO addPoints(@RequestBody ApplicationUserDTO body){
        ApplicationUser applicationUser = userRepository.findByUsername(body.getUsername()).get();
        int points = body.getPoints();
        return rewardPointService.addRewardPoints(applicationUser, points);
    }

    @PostMapping("/rewardredemption/view")
    public List<RewardRedemptionDTO> viewRewardRedemption(@RequestBody ApplicationUserDTO body){
        ApplicationUser applicationUser = userRepository.findById(body.getUserId()).get();
        return rewardRedemptionService.getRedemptionRecordsForUser(applicationUser);
    }

    @PostMapping("/rewardredemption/redemption")
    public RewardRedemptionDTO redeemRewards(@RequestBody RewardRedemptionDTO body){
        ApplicationUser applicationUser = userRepository.findByUsername(body.getUsername()).get();
        return rewardRedemptionService.redeemPoints(applicationUser, body.getItems(), body.getPoint());
    }

    @GetMapping("/user/role")
    public ResponseEntity<String> getUserRole(@RequestParam String username) {
        try {
            // Call the UserService to get the user by username
            ApplicationUser user = (ApplicationUser) userService.loadUserByUsername(username);
            
            // Check if the user has a role and return it in the response
            if (user != null && user.getAuthorities() != null && !user.getAuthorities().isEmpty()) {
                String role = user.getAuthorities().iterator().next().getAuthority(); // Assuming the user has only one role
                return ResponseEntity.ok(role);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User role not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user role");
        }
    }

    @PostMapping("/rewardredemption/redeemed_items_count")
    public ResponseEntity<?> viewRewardRedemption(@RequestBody RewardRedemptionDTO body) {
        Optional<ApplicationUser> applicationUserOpt = userRepository.findByUsername(body.getUsername());
        
        if (!applicationUserOpt.isPresent()) {
            // Handle the case where the user is not found
            return ResponseEntity.notFound().build();
        }

        ApplicationUser applicationUser = applicationUserOpt.get();
        List<RewardRedemption> redeemedItems = rewardRedemptionRepository.findByApplicationUser(applicationUser);
        
        // Assuming redeemedItemsCount returns a custom object that can be serialized to JSON
        Object response = rewardRedemptionService.redeemedItemsCount(redeemedItems);

        return ResponseEntity.ok(response);
    }


}

