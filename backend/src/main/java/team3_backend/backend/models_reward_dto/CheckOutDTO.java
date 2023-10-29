package team3_backend.backend.models_reward_dto;

import java.time.LocalDateTime;

public class CheckOutDTO {

    private Integer checkoutId;
    private String username;
    private String email;
    private LocalDateTime timestamp;

    // Default constructor
    public CheckOutDTO() {}

    // Parameterized constructor
    public CheckOutDTO(Integer checkoutId, String username, String email, LocalDateTime timestamp) {
        this.checkoutId = checkoutId;
        this.username = username;
        this.email = email;
        this.timestamp = timestamp;
    }

    // Getters and setters

    public Integer getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(Integer checkoutId) {
        this.checkoutId = checkoutId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
