package ml.autentication.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ml.autentication.configs.data.SecretData;
import ml.autentication.util.ProfileConstants;
import ml.autentication.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Configuration
public class SecretManager {


    @Autowired
    private SecurityConstants securityConstants;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    @Profile(ProfileConstants.NOT_LOCAL)
    public SecretData getSecret() throws JsonProcessingException {
        Region region = Region.of(Region.US_EAST_2.toString());

        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(securityConstants.getSecretArn())
                .build();

        GetSecretValueResponse getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        String secret = getSecretValueResult.secretString();

        return this.objectMapper.readValue(secret, SecretData.class);

    }

    @Bean
    @Profile(ProfileConstants.LOCAL)
    public SecretData getSecretLocal() throws JsonProcessingException {
        SecretData secretData = new SecretData();
        secretData.setSecret(securityConstants.getLocalSecret());

        return secretData;

    }

}
