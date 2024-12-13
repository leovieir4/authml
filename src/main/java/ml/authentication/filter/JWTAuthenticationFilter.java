package ml.authentication.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ml.authentication.configs.data.SecretData;
import ml.authentication.model.UserML;
import ml.authentication.util.SecurityConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private SecurityConstants securityConstants;

    private SecretData secretData;

    public JWTAuthenticationFilter(String url, AuthenticationManager authenticationManager, SecurityConstants securityConstants, SecretData secretData) {
        this.authenticationManager = authenticationManager;
        this.securityConstants = securityConstants;
        this.secretData = secretData;

        setFilterProcessesUrl(url);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            var v = request.getInputStream();
            UserML credenciais = new ObjectMapper().readValue(request.getInputStream(), UserML.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credenciais.getUsername(),
                            credenciais.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        Key key = Keys.hmacShaKeyFor(secretData.getSecret().getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setSubject(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + securityConstants.getExpiration_time()))
                .signWith(key) // Assine com a chave secreta
                .compact();

        response.getWriter().write(token);
        response.getWriter().flush();
    }
}