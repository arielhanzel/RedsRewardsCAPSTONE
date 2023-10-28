package team3_backend.backend.models_reward;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import team3_backend.backend.models.ApplicationUser;

@Entity
@Table(name = "ClassAttendances")
public class ClassAttendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendanceId;
    
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private ApplicationUser applicationUser;
    
    @ManyToOne
    @JoinColumn(name = "classId", nullable = false)
    private FitnessClass fitnessClass;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public FitnessClass getFitnessClass() {
        return fitnessClass;
    }

    public void setFitnessClass(FitnessClass fitnessClass) {
        this.fitnessClass = fitnessClass;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    // getters, setters, and other methods
}
