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
@Table(name = "CheckIns")
public class CheckIn {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer checkinId;
    
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private ApplicationUser applicationUser;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Integer getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(Integer checkinId) {
        this.checkinId = checkinId;
    }

    public String getUsernameFromCheckIn() {
        if (this.applicationUser != null) {
            return this.applicationUser.getUsername();
        } else {
            return null; // or throw an exception or handle this case as you see fit
        }
    }

    public String getEmailFromCheckIn() {
        if (this.applicationUser != null) {
            return this.applicationUser.getEmail();
        } else {
            return null; // or throw an exception or handle this case as you see fit
        }
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    // getters, setters, and other methods
}
