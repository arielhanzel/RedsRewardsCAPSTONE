package team3_backend.backend.reward_services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.CheckIn;
import team3_backend.backend.models_reward_dto.CheckInDTO;
import team3_backend.backend.reward_repository.CheckInRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;

    // Convert CheckIn entity to CheckInDTO
    private CheckInDTO convertToDTO(CheckIn checkIn) {
        return new CheckInDTO(
            checkIn.getCheckinId(),
            checkIn.getUsernameFromCheckIn(),
            checkIn.getEmailFromCheckIn(),
            checkIn.getTimestamp()
        );
    }

    // Create a new check-in
    @Transactional
    public CheckInDTO createCheckIn(ApplicationUser applicationUser) {
        CheckIn checkIn = new CheckIn();
        checkIn.setTimestamp(LocalDateTime.now());

        checkIn.setCheckinId(applicationUser.getUserId());

        CheckIn savedCheckIn = checkInRepository.save(checkIn);
        return convertToDTO(savedCheckIn);
    }

    // Get all check-ins for a specific user
    public List<CheckInDTO> getAllCheckInsForUser(ApplicationUser user) {
        List<CheckIn> checkIns = checkInRepository.findByApplicationUser(user);
        return checkIns.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }

    // Other service methods can be added as required, such as delete, update, etc.
}
