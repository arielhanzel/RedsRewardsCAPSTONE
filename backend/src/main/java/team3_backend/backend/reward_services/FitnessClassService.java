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

    @Autowired
    private final FitnessClassRepository fitnessClassRepository;

    public FitnessClassService(FitnessClassRepository fitnessClassRepository) {
        this.fitnessClassRepository = fitnessClassRepository;
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
    FitnessClass fitnessClass = new FitnessClass();
    fitnessClass.setType(type);
    fitnessClass.setTime(LocalTime.now());
    FitnessClass savedFitnessClass = fitnessClassRepository.save(fitnessClass);

    return convertToDTO(savedFitnessClass);
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
    public void deleteClass(Integer classId) {
        if (fitnessClassRepository.existsById(classId)) {
            fitnessClassRepository.deleteById(classId);
        } else {
            // You can throw a custom exception here to handle "not found" scenario.
            throw new RuntimeException("FitnessClass not found!");
        }
    }


    // You can add other methods as needed based on business requirements.
}
