package team3_backend.backend.models_reward_dto;

import java.time.LocalDateTime;

public class ClassAttendanceDTO {

    private Integer attendanceId;
    private Integer userId; // from ApplicationUser
    private String username; // from ApplicationUser
    private String email; // from ApplicationUser
    private String className; // from FitnessClass
    private LocalDateTime timestamp;

    public ClassAttendanceDTO() {}

    public ClassAttendanceDTO(Integer attendanceId,Integer userId, String username, String email, String className, LocalDateTime timestamp) {
        this.attendanceId = attendanceId;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.className = className;
        this.timestamp = timestamp;
    }

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

     public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // You can also override the toString(), equals(), and hashCode() methods if needed.
}
