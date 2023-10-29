package team3_backend.backend.models_reward_dto;

import java.util.Set;

public class ApplicationUserDTO {

    private Integer userId;
    private String username;
    private String email;
    private Set<String> roles;  // assuming the Role has a String representation (like role name)

    public ApplicationUserDTO() {
        // default constructor
    }

    public ApplicationUserDTO(Integer userId, String username, String email, Set<String> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    // You can also override the toString(), equals(), and hashCode() methods if needed.
}
