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
     *Response "{
                    "userId": 1,
                    "username": "admin",
                    "email": "admin@gmail.com",
                    "role": [
                        {
                            "roleId": 1,
                            "authority": "ADMIN"
                        }
                    ],
                    "jwt": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE3MDA1Mzk1NTEsInJvbGVzIjoiQURNSU4ifQ.G2ZEiBql16OsXtdbDqUXzIi1vSlwvX6dbLrPIz9D780C-zhAViYwbT2V_gRqgUNNGpDsnIzdowZxRcm0LTx0La4nfMfb_Fh1YeQCgqtu4jJItpHUGWV29Gl0yJ-zmpZ-CnkkuPC_2ntVS8LRXRd2to9VriPRNlng91NZmoDjyQ47sTLjex-W3QQkF2vX2ndRNvC18BmRRbgFVp9mz3IJeXZctvZpBYSg054BKVK5ub-PXuaWOl0zYl5uKQVOY0LymDXmnDCmI8iwD2vT16f5NRzptElnZsYrjToEcBV1l4Hk3ewv9_cHzfUlcZRIR2hW1SnYVWshLi9GroUdCbzrKg"
                }
    Tokenchanges after every logins
    */

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}   