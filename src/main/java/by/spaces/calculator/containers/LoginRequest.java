package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login request container")
public class LoginRequest {
    @Schema(description = "Login", example = "bibop")
    @NotBlank
    private String login;
    @Schema(description = "Password", example = "qwerty")
    @NotBlank
    private String password;
}
