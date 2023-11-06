package team3_backend.backend.models_reward;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "FitnessClasses")
public class FitnessClass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classId;
    
    @Column(nullable = false, length = 255)
    private String type;
    
    @Column(nullable = false)
    private LocalTime time;

    

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime localTime) {
        this.time = localTime;
    }

    
    // getters, setters, and other methods
}
