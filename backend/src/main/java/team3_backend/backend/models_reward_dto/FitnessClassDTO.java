package team3_backend.backend.models_reward_dto;

import java.time.LocalTime;

public class FitnessClassDTO {

    private Integer classId;
    private String type;
    private LocalTime time;

    public FitnessClassDTO() {}

    public FitnessClassDTO(Integer classId, String type, LocalTime time) {
        this.classId = classId;
        this.type = type;
        this.time = time;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    // You can also override the toString(), equals(), and hashCode() methods if needed.
}
