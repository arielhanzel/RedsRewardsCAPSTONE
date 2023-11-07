package team3_backend.backend.controllers;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body){
        ApplicationUser savedApplicationUser = authenticationService.registerUser(body.getUsername(), body.getPassword(), body.getEmail());

       if(savedApplicationUser != null){    
            //retriving and comparing role for admin role
            Collection<? extends GrantedAuthority> authorities = savedApplicationUser.getAuthorities();
            boolean isAdmin = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            
            boolean isUser = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));

            // Create a referral if a username is provided in the 'referral' field of RegistrationDTO
            if(body.getReferral() != null && !body.getReferral().isEmpty()){
                // Retrieve the referring user (referrer) by username
                ApplicationUser referrer = userRepository.findByUsername(body.getReferral())
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

                    if(isUser && !isAdmin)
                    {
                        // The user has Role_User Authority

                         if(savedReferral != null){
                            // add points to UnapprovedReward Entity of referral
                            unapprovedRewardService.addUnapprovedReward(referrer, 100);
                        }
                    }
                }
            }
        }
    

        return savedApplicationUser;
    }
    
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}   