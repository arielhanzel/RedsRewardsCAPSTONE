package team3_backend.backend.reward_services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.ClassAttendance;
import team3_backend.backend.models_reward.FitnessClass;
import team3_backend.backend.models_reward_dto.ClassAttendanceDTO;
import team3_backend.backend.reward_repository.ClassAttendanceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassAttendanceService {

    @Autowired
    private final ClassAttendanceRepository classAttendanceRepository;

    public ClassAttendanceService(ClassAttendanceRepository classAttendanceRepository) {
        this.classAttendanceRepository = classAttendanceRepository;
    }

    public List<ClassAttendanceDTO> getAllAttendances() {
        List<ClassAttendance> classAttendances = classAttendanceRepository.findAll();
        return classAttendances.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList()); 
    }

    public Optional<ClassAttendance> getAttendanceById(Integer attendanceId) {
        return classAttendanceRepository.findById(attendanceId);
    }

    public List<ClassAttendanceDTO> getClassAttendancesByUser(ApplicationUser user) {
        List<ClassAttendance> classAttendances = classAttendanceRepository.findByApplicationUser(user);
         return classAttendances.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());  
    }

    public List<ClassAttendance> getAttendancesByFitnessClass(FitnessClass fitnessClass) {
        return classAttendanceRepository.findByFitnessClass(fitnessClass);
    }

    private ClassAttendanceDTO convertToDTO(ClassAttendance classAttendance) {
        return new ClassAttendanceDTO(
            classAttendance.getAttendanceId(),
            classAttendance.getApplicationUser().getUserId(),
            classAttendance.getApplicationUser().getUsername(),
            classAttendance.getApplicationUser().getEmail(),
            classAttendance.getFitnessClass().getType(),
            classAttendance.getTimestamp()
        );
    }

    @Transactional
    public ClassAttendanceDTO createClassAttendance(ApplicationUser user, FitnessClass fitnessClass) {
        ClassAttendance classAttendance = new ClassAttendance();
        classAttendance.setApplicationUser(user);
        classAttendance.setFitnessClass(fitnessClass);
        classAttendance.setTimestamp(java.time.LocalDateTime.now());
        ClassAttendance savedClassAttendance = classAttendanceRepository.save(classAttendance);

        return convertToDTO(savedClassAttendance);
    }

    @Transactional
    public ClassAttendance updateAttendance(ClassAttendance classAttendance) {
        if (classAttendanceRepository.existsById(classAttendance.getAttendanceId())) {
            return classAttendanceRepository.save(classAttendance);
        } else {
            // You can throw a custom exception here to handle "not found" scenario.
            throw new RuntimeException("ClassAttendance not found!");
        }
    }

    @Transactional
    public void deleteAttendance(Integer attendanceId) {
        if (classAttendanceRepository.existsById(attendanceId)) {
            classAttendanceRepository.deleteById(attendanceId);
        } else {
            // You can throw a custom exception here to handle "not found" scenario.
            throw new RuntimeException("ClassAttendance not found!");
        }
    }

    // You can add other methods as needed based on business requirements.
}




