package team3_backend.backend.models_reward;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public ApplicationUser getApplicationUser() {
        return this.applicationUser;
    }
    // getters, setters, and other methods
}
