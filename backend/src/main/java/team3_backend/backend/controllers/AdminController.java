package team3_backend.backend.controllers;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @GetMapping("/")
    public String adminPage(){
        return "Hi user, you cannot alter database, sorry for that!";
    }

    @GetMapping("/checkin")
    public String checkin(){
        return "ok";
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
