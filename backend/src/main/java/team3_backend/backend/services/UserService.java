package team3_backend.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.FitnessClass;
import team3_backend.backend.models_reward_dto.ApplicationUserDTO;
import team3_backend.backend.repository.UserRepository;
import team3_backend.backend.reward_repository.FitnessClassRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FitnessClassRepository fitnessClassRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("In the user details service");

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }

   
    public ApplicationUserDTO registerClass(String username, String classType) {
        Optional<ApplicationUser> userOptional = userRepository.findByUsername(username);
        Optional<FitnessClass> fitnessClassOptional = fitnessClassRepository.findByType(classType);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        if (!fitnessClassOptional.isPresent()) {
            throw new IllegalStateException("Fitness class not found with type: " + classType);
        }

        ApplicationUser user = userOptional.get();

        FitnessClass fitnessClass = fitnessClassOptional.get();
        
        user.setFitnessClass(fitnessClass);
        ApplicationUser savedUser = userRepository.save(user); // Save the user with the updated FitnessClass
        
        return convertToDTO(savedUser); // Return the DTO of the updated user
    }

    private ApplicationUserDTO convertToDTO(ApplicationUser user) {
        
        // Assuming that FitnessClass has a getType method and user has getFitnessClass
        String classType = user.getFitnessClass() != null ? user.getFitnessClass().getType() : null;

        return new ApplicationUserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getAuthorities(),
                null,
                classType);
    }


    public ApplicationUserDTO userView(String username) {
        ApplicationUser savedUser = userRepository.findByUsername(username).get();
        return convertToDTO(savedUser);
    }


    public List<ApplicationUserDTO> allUserView() {
        List<ApplicationUser> savedUsers = userRepository.findAll();
        return savedUsers.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }
    

}

