package team3_backend.backend.controllers;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models.LoginResponseDTO;
import team3_backend.backend.models.RegistrationDTO;
import team3_backend.backend.models_reward.Referral;
import team3_backend.backend.models_reward_dto.ReferralDTO;
import team3_backend.backend.repository.UserRepository;
import team3_backend.backend.reward_services.ReferralService;
import team3_backend.backend.reward_services.RewardPointService;
import team3_backend.backend.reward_services.UnapprovedRewardService;
import team3_backend.backend.services.AuthenticationService;


@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ReferralService referralService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnapprovedRewardService unapprovedRewardService;




    /*
     *send {"username":"rajesh100",
                "password":"password",
                "email": "rajesh100gmail.com",
                "referrer":"rajesh2"
            }

    *response {
                    "userId": 3202,
                    "username": "rajesh100",
                    "password": "$2a$10$Kyqw..ya4gfTYc1QUzyNB.Fe0mb4D/kTTu1FiQhpIITV6hOLyKqwS",
                    "email": "rajesh100gmail.com",
                    "fitnessClass": null,
                    "authorities": [
                        {
                            "roleId": 2,
                            "authority": "USER"
                        }
                    ],
                    "enabled": true,
                    "accountNonExpired": true,
                    "credentialsNonExpired": true,
                    "accountNonLocked": true
                }
     */
    @PostMapping("/register")
    @Transactional
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body){
        ApplicationUser savedApplicationUser = authenticationService.registerUser(body.getUsername(), body.getPassword(), body.getEmail());

       if(savedApplicationUser != null){    
            
            // Create a referral if a username is provided in the 'referral' field of RegistrationDTO
            if(body.getReferrer() != null && !body.getReferrer().isEmpty()){
                // Retrieve the referring user (referrer) by username
                ApplicationUser referrer = userRepository.findByUsername(body.getReferrer())
                        .orElse(null);

                if(referrer != null){
                    // Register the referral
                    ReferralDTO savedReferral = referralService.registerReferral(referrer.getUserId(), savedApplicationUser.getUserId());

                    // Now you can handle logic for admin or non-admin as you have it in your controller
                    /*if (isAdmin) {
                        // The user has the ROLE_ADMIN authority

                        if(savedReferral != null){

                             // add points to RewardPoint Entity of referral
                             rewardPointService.addRewardPoints(referrer, 100);
                         
                        }
                    } */

                    
                        // The user has Role_User Authority

                         if(savedReferral != null){
                            // add points to UnapprovedReward Entity of referral
                            unapprovedRewardService.addUnapprovedReward(referrer, 100);
                        }
                    
                }
            }
        }
    

        return savedApplicationUser;
    }
    


    /*
     *send {"username":"admin","password":"password"}
     *Response will be "{
    "user": {
        "userId": 1,
        "username": "admin",
        "password": "$2a$10$V/MHnTmBy/GYaJ1mzVjeROx4mTBy3sOKK6wlxNVhQjtwLFV4qL0F2",
        "email": "admin@gmail.com",
        "fitnessClass": null,
        "authorities": [
            {
                "roleId": 1,
                "authority": "ADMIN"
            }
        ],
        "enabled": true,
        "accountNonExpired": true,
        "credentialsNonExpired": true,
        "accountNonLocked": true
    },
    "jwt": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE2OTk5NjkxODgsInJvbGVzIjoiQURNSU4ifQ.LzBvfCENov2MfsfwuQuO6zxic1rPSC8ffNV4UG9-MNtUxm93ay0woLB0TW1xwM5nqWxo8tjA3yrbzQc6fX1iVLwvpISpEf3t5AEbKmeaQ-uH3QI03NBLQeRpZUt2iOfbWao2yHeukWnUMyGkwIh6nfrHXFujlqiNikQQeN5hpM1O5KNKMDJF5Nb--nVfjF89bRX3cSe9D8h-ZATFikw26ixn-z1OKafwbucutOwhpNy8p5WL7RwIugW4UPw-fGNJrnmh4BfJR7P3dnBlQAg9eZeGm1423yU9nv_7nRBlpnGnJCLYsp9zwLsFuArLSo_L6c2WaI34vP8ZXR4qWk30QA""
     
    Tokenchanges after every logins
    */

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}   