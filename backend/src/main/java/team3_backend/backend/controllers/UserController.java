package team3_backend.backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @GetMapping("/")
    public String userPage(){
        return "Hi user, you cannot alter database, sorry for that!";
    }

    @GetMapping("/checkin")
    public String checkin(){
        return "ok";
    }
    
    @GetMapping("/fitnessclass")
    public String attendence(){
        return "ok";
    }

    @GetMapping("/rewardpoint")
    public String rewardpoint(){
        return "ok";
    }

    @GetMapping("/rewardredemption")
    public String rewardredemption(){
        return "ok";
    }


}

