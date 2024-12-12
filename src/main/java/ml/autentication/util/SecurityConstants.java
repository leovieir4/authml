package ml.autentication.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {

    @Value("${security.secret}")
    private String SECRET;

    @Value("${security.expiration-time}")
    private long EXPIRATION_TIME;

    @Value("${security.token-prefix}")
    private String TOKEN_PREFIX;

    @Value("${security.header-string}")
    private String HEADER_STRING;

    @Value("${security.rule-user}")
    private String RULE_USER;

    public String getRULE_USER() {
        return RULE_USER;
    }

    public void setRULE_USER(String RULE_USER) {
        this.RULE_USER = RULE_USER;
    }

    public String getHEADER_STRING() {
        return HEADER_STRING;
    }

    public void setHEADER_STRING(String HEADER_STRING) {
        this.HEADER_STRING = HEADER_STRING;
    }

    public String getTOKEN_PREFIX() {
        return TOKEN_PREFIX;
    }

    public void setTOKEN_PREFIX(String TOKEN_PREFIX) {
        this.TOKEN_PREFIX = TOKEN_PREFIX;
    }

    public long getEXPIRATION_TIME() {
        return EXPIRATION_TIME;
    }

    public void setEXPIRATION_TIME(long EXPIRATION_TIME) {
        this.EXPIRATION_TIME = EXPIRATION_TIME;
    }

    public String getSECRET() {
        return SECRET;
    }

    public void setSECRET(String SECRET) {
        this.SECRET = SECRET;
    }
}