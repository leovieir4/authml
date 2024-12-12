package ml.autentication.configs.data;

import org.springframework.stereotype.Component;

@Component
public class SecretData {

    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
