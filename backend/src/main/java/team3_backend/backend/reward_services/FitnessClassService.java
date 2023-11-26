package team3_backend.backend.reward_services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.FitnessClass;
import team3_backend.backend.models_reward_dto.FitnessClassDTO;
import team3_backend.backend.repository.UserRepository;
import team3_backend.backend.reward_repository.FitnessClassRepository;

@Service
public class FitnessClassService {

   
    private final FitnessClassRepository fitnessClassRepository;

    private final UserRepository userRepository;

    public FitnessClassService(FitnessClassRepository fitnessClassRepository, UserRepository userRepository) {
        this.fitnessClassRepository = fitnessClassRepository;
        this.userRepository = userRepository;
    }

    public List<FitnessClassDTO> getAllFitnessClasses() {
        List<FitnessClass> fitnessClasses = fitnessClassRepository.findAll(); // Retrieve all fitness classes
        return fitnessClasses.stream() // Stream the list
                             .map(this::convertToDTO) // Convert each FitnessClass entity to FitnessClassDTO
                             .collect(Collectors.toList()); // Collect the result into a List
    }

    public Optional<FitnessClass> getFitnessClassById(Integer classId) {
        return fitnessClassRepository.findById(classId);
    }

    public Optional<FitnessClass> getFitnessClassByType(String type) {
        return fitnessClassRepository.findByType(type);
    }

    private FitnessClassDTO convertToDTO(FitnessClass fitnessClass) {
        return new FitnessClassDTO(
            fitnessClass.getClassId(),
            fitnessClass.getType(),
            fitnessClass.getTime()
        );
    }

    @Transactional
    public FitnessClassDTO addFitnessClass(String type) {
        // Check if a FitnessClass with the given type already exists
        Optional<FitnessClass> existingFitnessClass = fitnessClassRepository.findByType(type);
    
        // If the type does not exist, create and save a new FitnessClass
        if (!existingFitnessClass.isPresent()) {
            FitnessClass fitnessClass = new FitnessClass();
            fitnessClass.setType(type);
            fitnessClass.setTime(LocalTime.now());
            FitnessClass savedFitnessClass = fitnessClassRepository.save(fitnessClass);
    
            return convertToDTO(savedFitnessClass);
        } else {
            // If the type already exists, handle it appropriately
            // For example, you might want to throw an exception or return null
            throw new IllegalStateException("Fitness class with type " + type + " already exists.");
        }
    }
   

    @Transactional
    public FitnessClass updateFitnessClass(FitnessClass fitnessClass) {
        if (fitnessClassRepository.existsById(fitnessClass.getClassId())) {
            return fitnessClassRepository.save(fitnessClass);
        } else {
            // You can throw a custom exception here to handle "not found" scenario.
            throw new RuntimeException("FitnessClass not found!");
        }
    }

    @Transactional
    public String deleteFitnessClass(Integer classId) {
        Optional<FitnessClass> fitnessClassOptional = fitnessClassRepository.findById(classId);

        if (fitnessClassOptional.isPresent()) {
            FitnessClass fitnessClass = fitnessClassOptional.get();

            // Remove the fitness class from associated users
            List<ApplicationUser> users = fitnessClass.getUsers();
            if (users != null) {
                for (ApplicationUser user : users) {
                    user.getFitnessClasses().remove(fitnessClass);
                    userRepository.save(user); // Assuming you have a userRepository to save the ApplicationUser
                }
            }

            // Now that the fitness class is disassociated from all users, it can be safely deleted
            fitnessClassRepository.deleteById(classId);
            return "Success";
        } else {
            // You can throw a custom exception here to handle "not found" scenario.
            throw new RuntimeException("FitnessClass not found!");
        }
    }


    // You can add other methods as needed based on business requirements.
}
