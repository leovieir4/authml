package ml.autentication.controller;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import ml.autentication.model.UserML;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Operation(summary = "Gerar token", description = "Retorna um token valido para autentica na API de CDS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "<TOKEN>",
                    content = @Content(examples = @ExampleObject(value = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdW" +
                            "IiOiJ0ZXN0ZSJ9.Shb7ssRwxBiTt6kG5QqkBVQko9t_jO_e4ygtBQEW6hU")))
    })
    @PostMapping("/gerarToken")
    public void gerarToken(@RequestBody UserML userML) {}

}
