package team3_backend.backend.models_reward_dto;

public class ReferralDTO {

    private Integer referralId;
    private String referrerUsername; // Assuming ApplicationUser has a getUsername() method
    private String refereeUsername;  // Assuming ApplicationUser has a getUsername() method

    public ReferralDTO() {}

    public ReferralDTO(Integer referralId, String referrerUsername, String refereeUsername) {
        this.referralId = referralId;
        this.referrerUsername = referrerUsername;
        this.refereeUsername = refereeUsername;
    }

    public Integer getReferralId() {
        return referralId;
    }

    public void setReferralId(Integer referralId) {
        this.referralId = referralId;
    }

    public String getReferrerUsername() {
        return referrerUsername;
    }

    public void setReferrerUsername(String referrerUsername) {
        this.referrerUsername = referrerUsername;
    }

    public String getRefereeUsername() {
        return refereeUsername;
    }

    public void setRefereeUsername(String refereeUsername) {
        this.refereeUsername = refereeUsername;
    }

    // You can also override the toString(), equals(), and hashCode() methods if needed.
}
