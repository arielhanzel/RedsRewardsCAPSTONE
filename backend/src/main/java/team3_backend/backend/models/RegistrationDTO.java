package team3_backend.backend.models;

public class RegistrationDTO {
    private String username;
    private String password;
    private String email;
    private String referral;

    public RegistrationDTO(){
        super();
    }

    public RegistrationDTO(String username, String password, String email, String referral){
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.referral = referral;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String toString(){
        return "Registration info: username: " + this.username + " password: " + this.password;
    }

}

