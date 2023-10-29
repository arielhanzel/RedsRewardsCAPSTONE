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
@Table(name = "CheckOuts")
public class CheckOut {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer checkoutId;
    
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private ApplicationUser applicationUser;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Integer getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(Integer checkoutId) {
        this.checkoutId = checkoutId;
    }

    public String getUsernameFromCheckOut() {
        if (this.applicationUser != null) {
            return this.applicationUser.getUsername();
        } else {
            return null; // or throw an exception or handle this case as you see fit
        }
    }

    public String getEmailFromCheckOut() {
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

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public ApplicationUser getApplicationUser() {
        return this.applicationUser;
    }
    
    // ... other methods can continue here
}
