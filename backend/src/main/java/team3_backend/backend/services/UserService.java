package team3_backend.backend.services;

import java.util.HashSet;
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
import jakarta.transaction.Transactional;
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

   @Transactional
   public ApplicationUserDTO registerClass(String username, String classType) {


        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        FitnessClass fitnessClass = fitnessClassRepository.findByType(classType)
                .orElseThrow(() -> new EntityNotFoundException("FitnessClass not found!"));

        // Add the fitness class to the user's set of fitness classes
        user.getFitnessClasses().add(fitnessClass);
        
        // Save the updated user
        
        return convertToDTO( userRepository.save(user)); // Return the DTO of the updated user
    }

    @Transactional
    public void removeFitnessClassFromUser(String username, String classType) {
        ApplicationUser user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new RuntimeException("User not found"));
        FitnessClass fitnessClass = fitnessClassRepository.findByType(classType)
                                .orElseThrow(() -> new RuntimeException("Class not found"));

        user.getFitnessClasses().remove(fitnessClass);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUserByUsername(String username) {
        ApplicationUser user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        userRepository.deleteById(user.getUserId());
    }
    
    private ApplicationUserDTO convertToDTO(ApplicationUser user) {

        return new ApplicationUserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getAuthorities(),
                null,
                user.getFitnessClasses().stream()
                       .map(FitnessClass::getType)
                       .collect(Collectors.toList()), 0);
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

