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
import team3_backend.backend.repository.UserRepository;
import team3_backend.backend.reward_repository.FitnessClassRepository;
import team3_backend.backend.reward_services.CheckInService;
import team3_backend.backend.reward_services.CheckOutService;
import team3_backend.backend.reward_services.ClassAttendanceService;
import team3_backend.backend.reward_services.FitnessClassService;
import team3_backend.backend.reward_services.ReferralService;
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
    

    @PostMapping("/")
    public String adminPage(){
        return "Admin Information";   
    }

    @PostMapping("/checkin/create")
    public CheckInDTO createcheckInUser(@RequestBody CheckInDTO body){
        return checkInService.createCheckIn(body.getUsername(),body.getEmail());
    }

    @PostMapping("/checkin/view")
    public List<CheckInDTO> viewCheckInUser(@RequestBody CheckInDTO body){
        return checkInService.getAllCheckInsForUser(body.getUsername(), body.getEmail());
    }

    @PostMapping("/checkin/allview")
    public List<CheckInDTO> viewCheckInAll(){
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
    public List<CheckOutDTO> viewCheckOutAll(){
        return checkOutService.getAllCheckOuts();
    }

    @PostMapping("/registerclass")
    public ApplicationUserDTO registerClass(@RequestBody ApplicationUserDTO body){
        ApplicationUserDTO savedApplicationUserDTO = userService.registerClass(body);
        if(savedApplicationUserDTO != null){
            //add reward points
        }

        return savedApplicationUserDTO;
        
    }

    @PostMapping("/fitnessclass")
    public List<FitnessClassDTO> fitnessClass() {
        return fitnessClassService.getAllFitnessClasses();
    }

    @PostMapping("/fitnessclass/add")
    public FitnessClassDTO addFitnessClass(@RequestBody FitnessClassDTO body) {
        return fitnessClassService.addFitnessClass(body.getType());
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

    @PostMapping("/classattendance/allview")
    public List<ClassAttendanceDTO> viewClassAttendanceAll(){
        return classAttendanceService.getAllAttendances();
    }

    @PostMapping("/referrer/view")
    public ReferralDTO viewReferrer(@RequestBody ApplicationUserDTO body){
        return referralService.viewReferrer(body.getUsername());
    }

    @PostMapping("/referree/view")
    public List<ReferralDTO> viewReferree(@RequestBody ApplicationUserDTO body){
        return referralService.viewReferree(body.getUsername());
    }

    @PostMapping("/referrel/view")
    public List<ReferralDTO> viewReferrals(){
        return referralService.viewReferrals();
    }

    @PostMapping("/unapprovedreward/view")
    public String point1(){
        return "ok";
    }

    @PostMapping("/unapprovedreward/allview")
    public String point3(){
        return "ok";
    }

     @PostMapping("/unapprovedreward/approve")
    public String point2(){
        return "ok";
    }

    @PostMapping("/rewardpoint/view")
    public String point4(){
        return "ok";
    }

    @PostMapping("/rewardpoint/allview")
    public String point5(){
        return "ok";
    }

    @PostMapping("/rewardredemption/view")
    public String rewardRedemption(){
        return "ok";
    }

     @PostMapping("/rewardredemption/allview")
    public String rewardRedemption1(){
        return "ok";
    }

     @PostMapping("/rewardeedemption/redemption")
    public String rewardRedemption2(){
        return "ok";
    }
    
}
