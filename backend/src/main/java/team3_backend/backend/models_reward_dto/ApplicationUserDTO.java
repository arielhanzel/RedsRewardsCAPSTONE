package team3_backend.backend.models_reward_dto;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public class ApplicationUserDTO {

    private Integer userId;
    private String username;
    private String email;
    Collection<? extends GrantedAuthority> roles;  // assuming the Role has a String representation (like role name)
    private String classType;
    private int points;
    private List<Integer> classIds;
    private List<String> classTypes;

    public ApplicationUserDTO() {
        // default constructor
        super();
    }

    public ApplicationUserDTO(Integer userId, String username, String email, Collection<? extends GrantedAuthority> roles, String classType, List<String> classTypes, int points) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.classType = classType;
        this.classTypes =classTypes;
        this.points = points;
    }

    // getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Integer> getClassIds() {
        return classIds;
    }

    public void setClassIds(List<Integer> classIds) {
        this.classIds = classIds;
    }

    public List<String> getClassTypes() {
        return classTypes;
    }

    public void setClassTypes(List<String> classTypes) {
        this.classTypes = classTypes;
    }

    // You can also override the toString(), equals(), and hashCode() methods if needed.
}
