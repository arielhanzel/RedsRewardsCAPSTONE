package team3_backend.backend.reward_services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.CheckIn;
import team3_backend.backend.models_reward.CheckOut;
import team3_backend.backend.models_reward_dto.CheckInDTO;
import team3_backend.backend.models_reward_dto.CheckOutDTO;
import team3_backend.backend.repository.UserRepository;
import team3_backend.backend.reward_repository.CheckOutRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckOutService {

    @Autowired
    private CheckOutRepository checkOutRepository;

    @Autowired
    private UserRepository userRepository;

    // Convert CheckOut entity to CheckOutDTO
    private CheckOutDTO convertToDTO(CheckOut checkOut) {
        ApplicationUser user = checkOut.getApplicationUser();
        return new CheckOutDTO(
            checkOut.getCheckoutId(), user.getUserId(),
            user.getUsername(),
            user.getEmail(),
            checkOut.getTimestamp()
        );
    }

    // Create a new check-out
    @Transactional
    public CheckOutDTO createCheckOut(String username, String email) {
        CheckOut checkOut = new CheckOut(); 
        checkOut.setTimestamp(LocalDateTime.now());

        Optional<ApplicationUser> user = userRepository.findByUsernameAndEmail(username, email);
        if (user.isPresent()) {
            checkOut.setApplicationUser(user.get());
        }

        CheckOut savedCheckOut = checkOutRepository.save(checkOut);
        return convertToDTO(savedCheckOut);
    }

    // Get all check-outs for a specific user
    public List<CheckOutDTO> getAllCheckOutsForUser(String username, String email) {
        ApplicationUser user = userRepository.findByUsernameAndEmail(username, email).get();
        List<CheckOut> checkOuts = checkOutRepository.findByApplicationUser(user);
        return checkOuts.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
    }

    //Get all CheckOut records
    public List<CheckOutDTO> getAllCheckOuts() {
        List<CheckOut> checkOuts = checkOutRepository.findAll(); // Get all CheckOut records from repository
        return checkOuts.stream()
                       .map(this::convertToDTO) // Convert each CheckOut entity to a CheckOutDTO
                       .collect(Collectors.toList()); // Collect the result into a List
    }

    // Additional service methods can be added as required, such as delete, update, etc.
}
