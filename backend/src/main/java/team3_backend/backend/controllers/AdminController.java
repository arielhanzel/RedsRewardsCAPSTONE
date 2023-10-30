package team3_backend.backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models.RegistrationDTO;
import team3_backend.backend.models_reward_dto.CheckInDTO;
import team3_backend.backend.models_reward_dto.CheckOutDTO;
import team3_backend.backend.repository.UserRepository;
import team3_backend.backend.reward_services.CheckInService;
import team3_backend.backend.services.AuthenticationService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private CheckInService checkOutService;
    

    @PostMapping("/")
    public String adminPage(){
        return "Admin_Page";   
    }

    @PostMapping("/checkin")
    public CheckInDTO checkInUser(@RequestBody CheckInDTO body){
         checkInService.createCheckIn(body.getUsername(),body.getEmail());
         return null;
    }

     @PostMapping("/checkout")
    public CheckOutDTO checkOutUser(@RequestBody CheckOutDTO body){
         //checkOutService.createCheckOut(body.getUsername(),body.getEmail());
         return null;
    }

     @GetMapping("/classattendance")
    public String classattendance(){
        return "ok";
    }

    @GetMapping("/fitnessclass")
    public String attendence(){
        return "ok";
    }

    @GetMapping("/rewardpoint")
    public String point(){
        return "ok";
    }

    @GetMapping("/rewardRedemption")
    public String rewardRedemption(){
        return "ok";
    }

    @GetMapping("/referral")
    public String referral(){
        return "ok";
    }
}
