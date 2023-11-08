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
import team3_backend.backend.models_reward.FitnessClass;
import team3_backend.backend.models_reward_dto.ApplicationUserDTO;
import team3_backend.backend.models_reward_dto.CheckInDTO;
import team3_backend.backend.models_reward_dto.CheckOutDTO;
import team3_backend.backend.models_reward_dto.ClassAttendanceDTO;
import team3_backend.backend.models_reward_dto.FitnessClassDTO;
import team3_backend.backend.models_reward_dto.ReferralDTO;
import team3_backend.backend.models_reward_dto.RewardPointDTO;
import team3_backend.backend.repository.UserRepository;
import team3_backend.backend.reward_repository.FitnessClassRepository;
import team3_backend.backend.reward_services.CheckInService;
import team3_backend.backend.reward_services.CheckOutService;
import team3_backend.backend.reward_services.ClassAttendanceService;
import team3_backend.backend.reward_services.FitnessClassService;
import team3_backend.backend.reward_services.ReferralService;
import team3_backend.backend.reward_services.RewardPointService;
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

    @GetMapping("/")
    public String userPage(){
        return "User information";
    }


    @PostMapping("/checkin/view")
    public List<CheckInDTO> viewCheckInUser(@RequestBody CheckInDTO body){
        return checkInService.getAllCheckInsForUser(body.getUsername(), body.getEmail());
    }


    @PostMapping("/checkout/create")
    public CheckOutDTO createcheckOutUser(@RequestBody CheckOutDTO body){
         return checkOutService.createCheckOut(body.getUsername(),body.getEmail());
    }

    @PostMapping("/checkout/view")
    public List<CheckOutDTO> viewCheckOutUser(@RequestBody CheckInDTO body){
    return checkOutService.getAllCheckOutsForUser(body.getUsername(), body.getEmail());
    }

    @PostMapping("/registerclass")
    public ApplicationUserDTO registerClass(@RequestBody ApplicationUserDTO body){
        ApplicationUserDTO savedApplicationUserDTO = userService.registerClass(body.getUsername(),body.getClassType());
        ApplicationUser applicationUser = userRepository.findById(savedApplicationUserDTO.getUserId()).get();
        if(savedApplicationUserDTO != null){
            unapprovedRewardService.addUnapprovedReward(applicationUser, 100);
        }
        return savedApplicationUserDTO;    
    }

    @PostMapping("/fitnessclass")
    public List<FitnessClassDTO> fitnessClass() {
        return fitnessClassService.getAllFitnessClasses();
    }

    
    
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


    @PostMapping("/classattendance/addpoints")
    public RewardPointDTO addPointsClassAttendance(@RequestBody ApplicationUserDTO body){
        return rewardPointService.addPointsClassAttendance(body.getUserId());
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
    public String point1(){
        return "ok";
    }


    @PostMapping("/rewardpoint/view")
    public String point4(){
        return "ok";
    }


    @PostMapping("/rewardredemption/view")
    public String rewardRedemption(){
        return "ok";
    }

     @PostMapping("/rewardeedemption/redemption")
    public String rewardRedemption2(){
        return "ok";
    }

}

