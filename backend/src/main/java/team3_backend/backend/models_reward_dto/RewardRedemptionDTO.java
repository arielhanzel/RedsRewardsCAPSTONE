package team3_backend.backend.models_reward_dto;

import java.time.LocalDateTime;

public class RewardRedemptionDTO {

    private Integer purchaseId;
    private String username; // Assuming ApplicationUser has a getUsername() method
    private String items;
    private Integer point;
    private LocalDateTime timestamp;

    public RewardRedemptionDTO() {}

    public RewardRedemptionDTO(Integer purchaseId, String username, String items, Integer point, LocalDateTime timestamp) {
        this.purchaseId = purchaseId;
        this.username = username;
        this.items = items;
        this.point = point;
        this.timestamp = timestamp;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // You can also override the toString(), equals(), and hashCode() methods if needed.
}
