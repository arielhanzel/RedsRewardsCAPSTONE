package team3_backend.backend.models_reward;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import team3_backend.backend.models.ApplicationUser;

@Entity
@Table(name = "Referrals")
public class Referral {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer referralId;
    
    @ManyToOne
    @JoinColumn(name = "ReferrerID", nullable = false)
    private ApplicationUser referrer;
    
    @ManyToOne
    @JoinColumn(name = "RefereeID", nullable = false)
    private ApplicationUser referee;

    public Integer getReferralId() {
        return referralId;
    }

    public void setReferralId(Integer referralId) {
        this.referralId = referralId;
    }

    public ApplicationUser getReferrer() {
        return referrer;
    }

    public void setReferrer(ApplicationUser referrer) {
        this.referrer = referrer;
    }

    public ApplicationUser getReferee() {
        return referee;
    }

    public void setReferee(ApplicationUser referee) {
        this.referee = referee;
    }
    
    // getters, setters, and other methods
}