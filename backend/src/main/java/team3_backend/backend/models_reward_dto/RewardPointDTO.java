package team3_backend.backend.models_reward_dto;

import java.time.LocalDateTime;

public class RewardPointDTO {

    private Integer pointId;
    private String username; // Assuming ApplicationUser has a getUsername() method
    private LocalDateTime timestamp;
    private Integer pointBalance;

    public RewardPointDTO() {}

    public RewardPointDTO(Integer pointId, String username, LocalDateTime timestamp, Integer pointBalance) {
        this.pointId = pointId;
        this.username = username;
        this.timestamp = timestamp;
        this.pointBalance = pointBalance;
    }

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getPointBalance() {
        return pointBalance;
    }

    public void setPointBalance(Integer pointBalance) {
        this.pointBalance = pointBalance;
    }

    // You can also override the toString(), equals(), and hashCode() methods if needed.
}
