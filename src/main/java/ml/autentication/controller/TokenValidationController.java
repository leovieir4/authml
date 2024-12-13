package ml.autentication.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ml.autentication.configs.data.SecretData;
import ml.autentication.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class TokenValidationController {

    @Autowired
    private SecurityConstants securityConstants;

    @Autowired
    private SecretData secretData;

    @PostMapping("/validarToken")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma mensagem informando se o token é ou não valido",
                    content = @Content(examples = @ExampleObject(value = "Token valido")))
    })
    public ResponseEntity<String> validarToken(@RequestBody @Schema(example = """
                                                        {
                                                          "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZSJ9.Shb7ssRwxBiTt6kG5QqkBVQko9t_jO_e4ygtBQEW6hU"
                                                        }
                                                        """) Map<String, String> payload) {

        String token = payload.get("token");

        if (token == null) {
            return ResponseEntity.badRequest().body("Token não fornecido.");
        }

        try {
            Key key = Keys.hmacShaKeyFor(secretData.getSecret().getBytes(StandardCharsets.UTF_8));

            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return ResponseEntity.ok("Token válido");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Tonken inválido");
        }
    }
}