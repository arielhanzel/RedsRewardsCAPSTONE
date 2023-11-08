package team3_backend.backend.models;

public class RegistrationDTO {
    private String username;
    private String password;
    private String email;
    private String referrer;

    public RegistrationDTO(){
        super();
    }

    public RegistrationDTO(String username, String password, String email, String referrer){
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.referrer = referrer;
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

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String toString(){
        return "Registration info: username: " + this.username + " password: " + this.password;
    }

}

