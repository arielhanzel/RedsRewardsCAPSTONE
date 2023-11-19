package team3_backend.backend.services;

import java.util.ArrayList;
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

import jakarta.persistence.EntityNotFoundException;
import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.FitnessClass;
import team3_backend.backend.models_reward_dto.ApplicationUserDTO;
import team3_backend.backend.models_reward_dto.FitnessClassDTO;
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

   
    public ApplicationUserDTO addClassToUser(String username, String classType) {
        ApplicationUser user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    
        FitnessClass fitnessClass = fitnessClassRepository.findByType(classType)
            .orElseThrow(() -> new EntityNotFoundException("Class not found with type: " + classType));
    
        // Prevent adding the same class multiple times
        if (!user.getFitnessClasses().contains(fitnessClass)) {
            user.getFitnessClasses().add(fitnessClass);
        }
    
        ApplicationUser savedUser = userRepository.save(user);
    
        return convertToDTO(savedUser);
    }

    private ApplicationUserDTO convertToDTO(ApplicationUser savedUser) {
        
        // Assuming that FitnessClass has a getType method and user has getFitnessClass
        List<FitnessClass> classInstances = savedUser.getFitnessClasses();
        List<String> classTypes = new ArrayList<>();  // Initialize the list

        for (FitnessClass fitnessClass : classInstances) {
            classTypes.add(fitnessClass.getType());  // Add the type to the list
        }
        

        return new ApplicationUserDTO(
                savedUser.getUserId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getAuthorities(),
                null,
                classTypes, 0);
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


    public List<FitnessClassDTO> getFitnessClassesForUser(String username) {
        ApplicationUser user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    
        return user.getFitnessClasses().stream()
                   .map(this::convertToFitnessClassDTO)
                   .collect(Collectors.toList());
    }
    
    private FitnessClassDTO convertToFitnessClassDTO(FitnessClass fitnessClass) {
        // Assuming you have a FitnessClassDTO with appropriate fields
        return new FitnessClassDTO(
                fitnessClass.getType()
        );
    }
    

}

