package team3_backend.backend.models_reward;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import team3_backend.backend.models.ApplicationUser;

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

    @ManyToMany(mappedBy = "fitnessClasses")
    private List<ApplicationUser> users;


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

    public List<ApplicationUser> getUsers() {
        return users;
    }

    public void setUsers(List<ApplicationUser> users) {
        this.users = users;
    }


    
    // getters, setters, and other methods
}
