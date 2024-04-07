package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login request container")
public class LoginRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
