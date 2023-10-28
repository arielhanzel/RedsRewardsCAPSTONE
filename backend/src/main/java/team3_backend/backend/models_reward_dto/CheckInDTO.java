package team3_backend.backend.models_reward_dto;

import java.time.LocalDateTime;

public class CheckInDTO {

    private Integer checkinId;
    private String username;
    private String email;
    private LocalDateTime timestamp;

    public CheckInDTO() {
        super();
    }

    public CheckInDTO(Integer checkinId, String username, String email, LocalDateTime timestamp) {
        this.checkinId = checkinId;
        this.username = username;
        this.email = email;
        this.timestamp = timestamp;
    }

    public Integer getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(Integer checkinId) {
        this.checkinId = checkinId;
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

    // You can also override the toString(), equals(), and hashCode() methods if needed.
}