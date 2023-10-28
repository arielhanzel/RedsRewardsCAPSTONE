package team3_backend.backend.models_reward_dto;

import java.time.LocalDateTime;

public class ClassAttendanceDTO {

    private Integer attendanceId;
    private String username; // from ApplicationUser
    private String className; // from FitnessClass
    private LocalDateTime timestamp;

    public ClassAttendanceDTO() {}

    public ClassAttendanceDTO(Integer attendanceId, String username, String className, LocalDateTime timestamp) {
        this.attendanceId = attendanceId;
        this.username = username;
        this.className = className;
        this.timestamp = timestamp;
    }

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
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
