package ml.autentication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserML {
    @Id
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}