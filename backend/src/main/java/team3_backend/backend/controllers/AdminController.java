package team3_backend.backend.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models.RegistrationDTO;
import team3_backend.backend.models_reward.ClassAttendance;
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

    @Autowired
    private RewardRedemptionRepository rewardRedemptionRepository;
    

    
    // send means Json data need to be sent from frontend to following api
    //jwt Token should be included in header, look at interfaced sign up and login example

    //response is data sent from backend to frontend aftermaking request
    
    
    //send jwt token to http://localhost:8000/admin/
    //response Admin Information
    @PostMapping("/")
    public String adminPage(){
        return "Admin Information";   
    }







    //User look up via username
    //send {"username":"rajesh"} + jwt Token
    /*response is ApplicationUserDTO in json format, eg. {
                                                    "userId": 2,
                                                    "username": "rajesh",
                                                    "email": "rajesh@gmail.com",
                                                    "roles": [
                                                        {
                                                            "roleId": 2,
                                                            "authority": "User"
                                                        }
                                                    ],
                                                    "classType": null,
                                                    "registeredClass": "Yoga"
                                                }
    */
    @PostMapping("/user/view")
    public ApplicationUserDTO userView(@RequestBody ApplicationUserDTO body){
        return userService.userView(body.getUsername());
    } 








    //View all users from database including admin
    //send jwt Token 
    /*
     * response [{
                    "userId": 1,
                    "username": "admin",
                    "email": "admin@gmail.com",
                    "roles": [
                        {
                            "roleId": 1,
                            "authority": "ADMIN"
                        }
                    ],
                    "classType": null,
                    "registeredClass": null
                },
                {
                    "userId": 2,
                    "username": "rajesh",
                    "email": "rajeshgmail.com",
                    "roles": [
                        {
                            "roleId": 2,
                            "authority": "USER"
                        }
                    ],
                    "classType": null,
                    "registeredClass": null
                },] 
    */
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







    //Add a new fitness Class in Fitness class database
    //send {"type":"yoga"} + Jwt Token 
    /*response  {
                    "classId": 1,
                    "type": "Yoga",
                    "time": "07:57:03.0707747"
                }
    */  
    @PostMapping("/fitnessclass/add")
    public FitnessClassDTO addFitnessClass(@RequestBody FitnessClassDTO body) {
        return fitnessClassService.addFitnessClass(body.getType());
    }



    @PostMapping("/fitnessclass/delete")
    public String deleteFitnessClass(@RequestBody FitnessClassDTO body) {
        FitnessClass fitnessClass = fitnessClassRepository.findByType(body.getType()).get();
        return fitnessClassService.deleteFitnessClass(fitnessClass.getClassId());
    }







   
   //view all available fitnessclasses from Fitness Class database
   //send Jwt Token
   /*
    response [
                {
                    "classId": 1,
                    "type": "Yoga",
                    "time": "07:57:03"
                },
                {
                    "classId": 2,
                    "type": "Gym",
                    "time": "07:58:55"
                }
            ]
    */
    @PostMapping("/fitnessclass")
    public List<FitnessClassDTO> fitnessClass() {
        return fitnessClassService.getAllFitnessClasses();
    }

   







    //Register a Fitness class by user
    //add 100 points to user, if registration success
    //send {"username": "rajesh", "classType":"Yoga"} + jwt Token
    /*
     * response {
                    "userId": 2,
                    "username": "rajesh",
                    "email": "rajeshgmail.com",
                    "roles": [
                        {
                            "roleId": 2,
                            "authority": "USER"
                        }
                    ],
                    "classType": null,
                    "registeredClass": "Yoga"
                }
     */
    @PostMapping("/registerclass")
    public ApplicationUserDTO registerClass(@RequestBody ApplicationUserDTO body){
        ApplicationUserDTO savedApplicationUserDTO = userService.registerClass(body.getUsername(),body.getClassType());
        ApplicationUser applicationUser = userRepository.findById(savedApplicationUserDTO.getUserId()).get();
        if(savedApplicationUserDTO != null){
            unapprovedRewardService.addUnapprovedReward(applicationUser, 100);
        }
        return savedApplicationUserDTO;    
    }

    @PostMapping("/registeredclasses")
    public List<String> registeredClasses(@RequestBody ApplicationUserDTO body) {
    ApplicationUser user = userRepository.findByUsername(body.getUsername()).get();
    List<FitnessClass> registeredClasses = user.getFitnessClasses();

    // Initialize the classes list outside the loop
    List<String> classes = new ArrayList<>();

    // Loop through each registered class and add its type to the classes list
    for (FitnessClass registeredClass : registeredClasses) {
        classes.add(registeredClass.getType());
    }

    return classes;
    }

    @PostMapping("/unregisterclass")
    public ResponseEntity<?> unregisteredClasses(@RequestBody ApplicationUserDTO body) { 
        userService.removeFitnessClassFromUser(body.getUsername(), body.getClassType());
        return ResponseEntity.ok().build();
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
    //send {"username":"rajesh2"} + jwt token
    /*
     * response {
                    "referralId": 1,
                    "referrerUsername": "admin",
                    "refereeUsername": "rajesh2"
                }
     */
    @PostMapping("/referrer/view")
    public ReferralDTO viewReferrer(@RequestBody ApplicationUserDTO body){
        return referralService.viewReferrer(body.getUsername());
    }









    //to view list of referee, how many users are reffered by the user
    //send {"username":"admin"} + jwt token
    /*
     * response [
                    {
                        "referralId": 1,
                        "referrerUsername": "admin",
                        "refereeUsername": "rajesh2"
                    },
                    {
                        "referralId": 33,
                        "referrerUsername": "admin",
                        "refereeUsername": "rajesh3"
                    },
                    {
                        "referralId": 34,
                        "referrerUsername": "admin",
                        "refereeUsername": "rajesh4"
                    }
                ]
     */
    @PostMapping("/referree/view")
    public List<ReferralDTO> viewReferree(@RequestBody ApplicationUserDTO body){
        return referralService.viewReferree(body.getUsername());
    }






    
    
    //List all instances of referral database
    //send  jwt token
    /*
     * response [
                    {
                        "referralId": 1,
                        "referrerUsername": "admin",
                        "refereeUsername": "rajesh2"
                    },
                    {
                        "referralId": 33,
                        "referrerUsername": "admin",
                        "refereeUsername": "rajesh3"
                    },
                    {
                        "referralId": 34,
                        "referrerUsername": "admin",
                        "refereeUsername": "rajesh4"
                    },
                    {
                        "referralId": 35,
                        "referrerUsername": "rajesh1",
                        "refereeUsername": "rajesh5"
                    },
                ]
     */
    @PostMapping("/referral/view")
    public List<ReferralDTO> viewReferrals(){
        return referralService.viewReferrals();
    }










    //Allready implemented while registering a new user from authentication service
    //this is automated while user register with referrer name add point will be added to referrer in unapprovedpoints database
    @PostMapping("/referrer/addPoints")
    public RewardPointDTO addPointsReferrer(@RequestBody ApplicationUserDTO body){
        return rewardPointService.addPointsReferrer(body.getUsername());
    }











    //During new sign up, 100 points will be added to refferrer. This points willbe stored in unapproved reward database.
    //to view unapproved reward of a user
    //send {"username":"admin"} + jwt token
    /*
     * response [
                    {
                        "pointId": 34,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:11:43.975879",
                        "pointBalance": 100
                    }
                ]
     */
    @PostMapping("/unapprovedreward/view")
    public List<UnapprovedRewardDTO> viewUnapprovedReward(@RequestBody ApplicationUserDTO body){
        ApplicationUser applicationUser = userRepository.findByUsername(body.getUsername()).get();
        return unapprovedRewardService.getAllUnapprovedRewardsForUser(applicationUser);
    }








    //To view unapproved reward of all user
    //send jwt token
    /*
     * response [
                    {
                        "pointId": 34,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:11:43.975879",
                        "pointBalance": 100
                    },
                    {
                        "pointId": 35,
                        "username": "rajesh1",
                        "timestamp": "2023-11-10T11:12:04.21923",
                        "pointBalance": 100
                    },
                    {
                        "pointId": 65,
                        "username": "rajesh1",
                        "timestamp": "2023-11-10T11:12:57.633262",
                        "pointBalance": 100
                    },
                    {
                        "pointId": 66,
                        "username": "rajesh2",
                        "timestamp": "2023-11-10T11:13:20.889369",
                        "pointBalance": 100
                    },
                    {
                        "pointId": 67,
                        "username": "rajesh2",
                        "timestamp": "2023-11-14T07:44:23.566965",
                        "pointBalance": 100
                    }
                ]
     */
    @PostMapping("/unapprovedreward/allview")
    public List<UnapprovedRewardDTO> viewAllUnapprovedreward(){
        return unapprovedRewardService.getAllUnapprovedRewards();
    }











    //to view approved reward points of user
    //send {"username":"admin"} + jwt token
    /*
     * response [
                    {
                        "pointId": 1,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:19:39.363382",
                        "pointBalance": 100
                    },
                    {
                        "pointId": 2,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:20:25.180736",
                        "pointBalance": 100
                    },
                     {
                        "pointId": 3,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:35:12.829357",
                        "pointBalance": 100
                    },
                    {
                        "pointId": 4,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:35:14.815092",
                        "pointBalance": 100
                    },
                    {
                        "pointId": 5,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:38:26.419819",
                        "pointBalance": -100
                    },
                    {
                        "pointId": 6,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:40:20.909721",
                        "pointBalance": -100
                    }
                ]
     */
    @PostMapping("/rewardpoint/view")
    public List<RewardPointDTO> viewRewardPoint(@RequestBody ApplicationUserDTO body){
        ApplicationUser applicationUser = userRepository.findByUsername(body.getUsername()).get();
        return rewardPointService.getAllRewardPointsForUser(applicationUser);
    }










    //to view total points of user
    //send {"username": "admin"} + jwt Token
    /*
     * response 200
     */
    @PostMapping("/rewardpoint/totalpoints")
    public Integer viewTotalPoints(@RequestBody ApplicationUserDTO body){
        ApplicationUser applicationUser = userRepository.findByUsername(body.getUsername()).get();
        return rewardPointService.getTotalRewardPointsForUser(applicationUser);
    }






    

    //to view approved reward point of all users from reward point database
    //send jwt token
    /*
     * response [
                    {
                        "pointId": 1,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:19:39.363382",
                        "pointBalance": 100
                    },
                    {
                        "pointId": 2,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:20:25.180736",
                        "pointBalance": 100
                    },
                    {
                        "pointId": 3,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:35:12.829357",
                        "pointBalance": 100
                    },
                    {
                        "pointId": 4,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:35:14.815092",
                        "pointBalance": 100
                    },
                    {
                        "pointId": 5,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:38:26.419819",
                        "pointBalance": -100
                    },
                    {
                        "pointId": 6,
                        "username": "admin",
                        "timestamp": "2023-11-10T11:40:20.909721",
                        "pointBalance": -100
                    },
                    {
                        "pointId": 7,
                        "username": "rajesh",
                        "timestamp": "2023-11-14T08:07:57.710544",
                        "pointBalance": 100
                    }
                ]
     */
    @PostMapping("/rewardpoint/allview")
    public List<RewardPointDTO> viewAllRewardPoint(){
        return rewardPointService.getAllRewardPoints();
    }










    //to approve unapproved reward by admin
    //it adds point to rewardpoint database for a user
    //then it deletes points from unapproved reward database.
    //send {"pointId": 67, "username":"rajesh2", "pointBlance":100} + jwt token
    /*
     * response {
                    "pointId": 8,
                    "username": "rajesh2",
                    "timestamp": "2023-11-14T09:10:55.5891391",
                    "pointBalance": 100
                }
     */
    @PostMapping("/unapprovedreward/approve")
    public RewardPointDTO approveRewardPoint(@RequestBody UnapprovedRewardDTO body){
        return rewardApprovalService.approveRewardPoint(body.getPointId(), body.getUsername(), body.getPointBalance());
    }







    //to view the list of reward redemption made by user
    //send {"username":"admin"} +jwt Token
    /*
     * response [
                    {
                        "purchaseId": 1,
                        "username": "admin",
                        "items": "T-shirt",
                        "point": 100,
                        "timestamp": "2023-11-10T11:38:26.416838"
                    },
                    {
                        "purchaseId": 2,
                        "username": "admin",
                        "items": "T-shirt",
                        "point": 100,
                        "timestamp": "2023-11-10T11:40:20.907279"
                    }
                ]
     */
    @PostMapping("/rewardredemption/view")
    public List<RewardRedemptionDTO> viewRewardRedemption(@RequestBody ApplicationUserDTO body){
        ApplicationUser applicationUser = userRepository.findByUsername(body.getUsername()).get();
        return rewardRedemptionService.getRedemptionRecordsForUser(applicationUser);
    }








    //to view list of reward redemption made by all user from reward redemption database
    //send jwt token
    /*
     * response [
                    {
                        "purchaseId": 1,
                        "username": "admin",
                        "items": "T-shirt",
                        "point": 100,
                        "timestamp": "2023-11-10T11:38:26.416838"
                    },
                    {
                        "purchaseId": 2,
                        "username": "admin",
                        "items": "T-shirt",
                        "point": 100,
                        "timestamp": "2023-11-10T11:40:20.907279"
                    },
                    {
                        "purchaseId": 3,
                        "username": "rajesh2",
                        "items": "T-shirt",
                        "point": 100,
                        "timestamp": "2023-11-14T09:23:52.986745"
                    },
                    {
                        "purchaseId": 4,
                        "username": "rajesh",
                        "items": "T-shirt",
                        "point": 100,
                        "timestamp": "2023-11-14T09:30:21.738243"
                    }
                ]
     */
    @PostMapping("/rewardredemption/allview")
    public List<RewardRedemptionDTO> viewAllRewardRedemption(){
        return rewardRedemptionService.getALLRedemptionRecords();
    }
    






    //to redeem rewards. it stores the redemption instance in reward redemption database
    //it reduce the point of the user if redemption successful
    //send {"username": "rajesh", "items":"T-shirt", "point":200} + jwt token
    /*
     * response {
                    "purchaseId": 4,
                    "username": "rajesh",
                    "items": "T-shirt",
                    "point": 100,
                    "timestamp": "2023-11-14T09:30:21.7382428"
                }
     */
    @PostMapping("/rewardredemption/redemption")
    public RewardRedemptionDTO redeemRewards(@RequestBody RewardRedemptionDTO body){
        ApplicationUser applicationUser = userRepository.findByUsername(body.getUsername()).get();
        return rewardRedemptionService.redeemPoints(applicationUser, body.getItems(), body.getPoint());
    }
    

    //to check numbers of items redemmed by user
    //send {"username":"rajesh"} + jwt Token
    //response {{"T-shirt" : 2,"drintSnack" : 2} }
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

    @PostMapping("/delete/user")
    public ResponseEntity<?> deleteUserByUsername(@RequestBody ApplicationUserDTO body) {
        try {
            userService.deleteUserByUsername(body.getUsername());
            return ResponseEntity.ok().build();
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
