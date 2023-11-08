package team3_backend.backend.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models.RegistrationDTO;
import team3_backend.backend.models_reward.ClassAttendance;
import team3_backend.backend.models_reward.FitnessClass;
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
import team3_backend.backend.reward_services.CheckInService;
import team3_backend.backend.reward_services.CheckOutService;
import team3_backend.backend.reward_services.ClassAttendanceService;
import team3_backend.backend.reward_services.FitnessClassService;
import team3_backend.backend.reward_services.ReferralService;
import team3_backend.backend.reward_services.RewardApprovalService;
import team3_backend.backend.reward_services.RewardPointService;
import team3_backend.backend.reward_services.RewardRedemptionService;
import team3_backend.backend.reward_services.UnapprovedRewardService;
import team3_backend.backend.services.AuthenticationService;
import team3_backend.backend.services.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

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
    

    @PostMapping("/")
    public String adminPage(){
        return "Admin Information";   
    }


    // send means Json data need to be sent from frontend to following api
    //jwt Token should be included in header, look at interfaced sign up and login example
    
    //User look up via username
    //send {"username":"Alex"} + jwt Token
    @PostMapping("/user/view")
    public ApplicationUserDTO userView(@RequestBody ApplicationUserDTO body){
        return userService.userView(body.getUsername());
    }

    //View all users from database including admin
    //send jwt Token
    @PostMapping("/user/allview")
    public List<ApplicationUserDTO> allUserView(){
        return userService.allUserView();
    }


    /* Not Required to implement
    
    @PostMapping("/checkin/create")
    public CheckInDTO createcheckInUser(@RequestBody CheckInDTO body){
        return checkInService.createCheckIn(body.getUsername(),body.getEmail());
    }

    @PostMapping("/checkin/view")
    public List<CheckInDTO> viewCheckInUser(@RequestBody CheckInDTO body){
        return checkInService.getAllCheckInsForUser(body.getUsername(), body.getEmail());
    }

    @PostMapping("/checkin/allview")
    public List<CheckInDTO> viewAllCheckIn(){
        return checkInService.getAllCheckIns();
    }

    @PostMapping("/checkout/create")
    public CheckOutDTO createcheckOutUser(@RequestBody CheckOutDTO body){
         return checkOutService.createCheckOut(body.getUsername(),body.getEmail());
    }

    @PostMapping("/checkout/view")
    public List<CheckOutDTO> viewCheckOutUser(@RequestBody CheckInDTO body){
    return checkOutService.getAllCheckOutsForUser(body.getUsername(), body.getEmail());
    }

    @PostMapping("/checkout/allview")
    public List<CheckOutDTO> viewALLCheckOut(){
        return checkOutService.getAllCheckOuts();
    }

    */

   
   //view all available fitnessclasses from Fitness Class database
   //send Jwt Token
    @PostMapping("/fitnessclass")
    public List<FitnessClassDTO> fitnessClass() {
        return fitnessClassService.getAllFitnessClasses();
    }

    //Add a new fitness Class in Fitness class database
    //send {"type":"yoga"} + Jwt Token  
    @PostMapping("/fitnessclass/add")
    public FitnessClassDTO addFitnessClass(@RequestBody FitnessClassDTO body) {
        return fitnessClassService.addFitnessClass(body.getType());
    }

    //Register a Fitness class by user
    //add 100 points to user, if registration success
    //send {"username": "username", "classType"="Yoga"} + jwt Token
    @PostMapping("/registerclass")
    public ApplicationUserDTO registerClass(@RequestBody ApplicationUserDTO body){
        ApplicationUserDTO savedApplicationUserDTO = userService.registerClass(body.getUsername(),body.getClassType());
        ApplicationUser applicationUser = userRepository.findById(savedApplicationUserDTO.getUserId()).get();
        if(savedApplicationUserDTO != null){
            rewardPointService.addRewardPoints(applicationUser, 100);
        }
        return savedApplicationUserDTO;    
    }

    /* Not required to implement

    @PostMapping("/classattendance/create")
    public ClassAttendanceDTO createClassAttendance(@RequestBody ClassAttendanceDTO body){
        ApplicationUser user = userRepository.findByUsernameAndEmail(body.getUsername(), body.getEmail()).get();
        FitnessClass fitnessClass = fitnessClassRepository.findByType(body.getClassName()).get();
        return classAttendanceService.createClassAttendance(user, fitnessClass);
    }

    @PostMapping("/classattendance/view")
    public List<ClassAttendanceDTO> getClassAttendanceByUser(@RequestBody ClassAttendanceDTO body){
         ApplicationUser user = userRepository.findByUsernameAndEmail(body.getUsername(), body.getEmail()).get();
        return classAttendanceService.getClassAttendancesByUser(user);
    }

    @PostMapping("/classattendance/allview")
    public List<ClassAttendanceDTO> viewAllClassAttendance(){
        return classAttendanceService.getAllAttendances();
    }

    @PostMapping("/classattendance/addpoints")
    public RewardPointDTO addPointsClassAttendance(@RequestBody ApplicationUserDTO body){
        return rewardPointService.addPointsClassAttendance(body.getUserId());
    }

    */

    //to view referrer, who refer the user
    //send {"username":"Alex"} + jwt token
    @PostMapping("/referrer/view")
    public ReferralDTO viewReferrer(@RequestBody ApplicationUserDTO body){
        return referralService.viewReferrer(body.getUsername());
    }

    //to view list of referee, how many users are reffered by the user
    //send {"username":"Alex"} + jwt token
    @PostMapping("/referree/view")
    public List<ReferralDTO> viewReferree(@RequestBody ApplicationUserDTO body){
        return referralService.viewReferree(body.getUsername());
    }

    //List all instances of referral database
    //send {"username":"Alex"} + jwt token
    @PostMapping("/referral/view")
    public List<ReferralDTO> viewReferrals(){
        return referralService.viewReferrals();
    }

    //Allready implemented while registering a new user
    @PostMapping("/referrer/addPoints")
    public RewardPointDTO addPointsReferrer(@RequestBody ApplicationUserDTO body){
        return rewardPointService.addPointsReferrer(body.getUsername());
    }

    //During new sign up, 100 points will be added to refferrer. This points willbe stored in unapproved reward database.
    //to view unapproved reward of a user
    //send {"username":"Alex"} + jwt token
    @PostMapping("/unapprovedreward/view")
    public List<UnapprovedRewardDTO> viewUnapprovedReward(@RequestBody ApplicationUserDTO body){
        ApplicationUser applicationUser = userRepository.findByUsername(body.getUsername()).get();
        return unapprovedRewardService.getAllUnapprovedRewardsForUser(applicationUser);
    }

    //To view unapproved reward of all user
    //send jwt token
    @PostMapping("/unapprovedreward/allview")
    public List<UnapprovedRewardDTO> viewAllUnapprovedreward(){
        return unapprovedRewardService.getAllUnapprovedRewards();
    }

    //to view approved reward points of user
    //send {"username":"Alex"} + jwt token
    @PostMapping("/rewardpoint/view")
    public List<RewardPointDTO> viewRewardPoint(@RequestBody ApplicationUserDTO body){
        ApplicationUser applicationUser = userRepository.findByUsername(body.getUsername()).get();
        return rewardPointService.getAllRewardPointsForUser(applicationUser);
    }

    //to view approved reward point of all users from reward point database
    //send jwt token
    @PostMapping("/rewardpoint/allview")
    public List<RewardPointDTO> viewAllRewardPoint(){
        return rewardPointService.getAllRewardPoints();
    }

    //to approve unapproved reward by admin
    //it adds point to rewardpoint database for a user
    //then it deletes points from unapproved reward database.
    //send {"pointId": 1, "username":"Alex", "pointBlance":100} + jwt token
    @PostMapping("/unapprovedreward/approve")
    public RewardPointDTO approveRewardPoint(@RequestBody UnapprovedRewardDTO body){
        return rewardApprovalService.approveRewardPoint(body.getPointId(), body.getUsername(), body.getPointBalance());
    }

    //to view the list of reward redemption made by user
    //send {"username":"Alex"} +jwt Token
    @PostMapping("/rewardredemption/view")
    public List<RewardRedemptionDTO> viewRewardRedemption(@RequestBody ApplicationUserDTO body){
        ApplicationUser applicationUser = userRepository.findByUsername(body.getUsername()).get();
        return rewardRedemptionService.getRedemptionRecordsForUser(applicationUser);
    }

    //to view list of reward redemption made by all user from reward redemption database
    //send jwt token
    @PostMapping("/rewardredemption/allview")
    public List<RewardRedemptionDTO> viewAllRewardRedemption(){
        return rewardRedemptionService.getALLRedemptionRecords();
    }

    //to redeem rewards. it stores the redemption instance in reward redemption database
    //it reduce the point of the user if redemption successful
    //send {"username": "Alex", "items":"T-shirt", "point":200} + jwt token
    @PostMapping("/rewardeedemption/redemption")
    public RewardRedemptionDTO redeemRewards(@RequestBody RewardRedemptionDTO body){
        ApplicationUser applicationUser = userRepository.findByUsername(body.getUsername()).get();
        return rewardRedemptionService.redeemPoints(applicationUser, body.getItems(), body.getPoint());
    }

    
    
}
