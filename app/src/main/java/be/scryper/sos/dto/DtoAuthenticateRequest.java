package be.scryper.sos.dto;

public class DtoAuthenticateRequest {

    private String email;
    private String password;

    public DtoAuthenticateRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "DtoAuthenticateRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
