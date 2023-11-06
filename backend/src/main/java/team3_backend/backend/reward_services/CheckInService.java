package team3_backend.backend.reward_services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.CheckIn;
import team3_backend.backend.models_reward_dto.CheckInDTO;
import team3_backend.backend.repository.UserRepository;
import team3_backend.backend.reward_repository.CheckInRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private UserRepository userRepository;



    //// Convert CheckIn entity list to CheckInDTO
    private CheckInDTO convertToDTO(CheckIn checkIn) {
        ApplicationUser user = checkIn.getApplicationUser();
        return new CheckInDTO(
            checkIn.getCheckinId(),
            user.getUserId(),user.getUsername(),user.getEmail(), checkIn.getTimestamp()
        );
    }

    // Create a new check-in
    @Transactional
    public CheckInDTO createCheckIn(String username, String email) {
        CheckIn checkIn = new CheckIn(); 
        checkIn.setTimestamp(LocalDateTime.now());

        Optional<ApplicationUser> user = userRepository.findByUsernameAndEmail(username,email);
        if (user.isPresent()) {
        checkIn.setApplicationUser(user.get());
}

        CheckIn savedCheckIn = checkInRepository.save(checkIn);
        
        return convertToDTO(savedCheckIn);
    }

    

    // Get all check-ins for a specific user
    public List<CheckInDTO> getAllCheckInsForUser(String username, String email) {
        ApplicationUser user = userRepository.findByUsernameAndEmail(username, email).get();
        List<CheckIn> checkIns = checkInRepository.findByApplicationUser(user);
        return checkIns.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }

    //Get all CheckIn records
    public List<CheckInDTO> getAllCheckIns() {
        List<CheckIn> checkIns = checkInRepository.findAll(); // Get all CheckIn records from repository
        return checkIns.stream()
                       .map(this::convertToDTO) // Convert each CheckIn entity to a CheckInDTO
                       .collect(Collectors.toList()); // Collect the result into a List
    }

    // Other service methods can be added as required, such as delete, update, etc.
}
