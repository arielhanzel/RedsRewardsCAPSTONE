package team3_backend.backend.reward_repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team3_backend.backend.models.ApplicationUser;
import team3_backend.backend.models_reward.ClassAttendance;
import team3_backend.backend.models_reward.FitnessClass;

@Repository
public interface ClassAttendanceRepository extends JpaRepository<ClassAttendance, Integer> {
    // You can define custom query methods here if needed.

    // to find all attendances of a specific user
    List<ClassAttendance> findByApplicationUser(ApplicationUser user);

    // to find all attendances for a specific fitness class
    List<ClassAttendance> findByFitnessClass(FitnessClass fitnessClass);

}
