package ml.authentication.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {

    @Value("${security.secret}")
    private String localSecret;

    @Value("${security.expiration-time}")
    private long expiration_time;

    @Value("${security.token-prefix}")
    private String tokenPrefix;

    @Value("${security.header}")
    private String header;

    @Value("${security.rule-user}")
    private String ruleUser;

    @Value("${security.secret-arn}")
    private String secretArn;

    public String getRuleUser() {
        return ruleUser;
    }

    public void setRuleUser(String ruleUser) {
        this.ruleUser = ruleUser;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public long getExpiration_time() {
        return expiration_time;
    }

    public void setExpiration_time(long expiration_time) {
        this.expiration_time = expiration_time;
    }

    public String getLocalSecret() {
        return localSecret;
    }

    public void setLocalSecret(String localSecret) {
        this.localSecret = localSecret;
    }

    public String getSecretArn() {
        return secretArn;
    }

    public void setSecretArn(String secretArn) {
        this.secretArn = secretArn;
    }
}