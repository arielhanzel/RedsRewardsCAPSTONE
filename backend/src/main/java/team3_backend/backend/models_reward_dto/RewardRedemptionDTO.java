package team3_backend.backend.models_reward_dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RewardRedemptionDTO {

    private Integer purchaseId;
    private String username; // Assuming ApplicationUser has a getUsername() method
    private String items;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public RewardRedemptionDTO() {}

    public RewardRedemptionDTO(Integer purchaseId, String username, String items, BigDecimal amount, LocalDateTime timestamp) {
        this.purchaseId = purchaseId;
        this.username = username;
        this.items = items;
        this.amount = amount;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // You can also override the toString(), equals(), and hashCode() methods if needed.
}
