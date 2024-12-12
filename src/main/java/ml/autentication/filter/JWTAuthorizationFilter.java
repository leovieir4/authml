package ml.autentication.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ml.autentication.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private SecurityConstants securityConstants;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, SecurityConstants securityConstants) {
        super(authenticationManager);
        this.securityConstants = securityConstants;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(securityConstants.getHEADER_STRING());

        var url = request.getRequestURI();

        if (header == null || !header.startsWith(securityConstants.getTOKEN_PREFIX())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(securityConstants.getHEADER_STRING());

        if (token != null) {
            try {
                Key key = Keys.hmacShaKeyFor(securityConstants.getSECRET().getBytes(StandardCharsets.UTF_8));

                // Valida o token e extrai as claims
                Jws<Claims> jwsClaims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token.replace(securityConstants.getTOKEN_PREFIX(), ""));

                // Extrai o username do token
                String username = jwsClaims.getBody().getSubject();

                if (username != null) {
                    // Passa o username como principal
                    return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                }

            } catch (JwtException e) {
                // Token inválido - logue o erro ou retorne uma resposta de erro
                return null;
            }
        }
        return null;
    }
}