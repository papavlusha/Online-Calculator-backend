package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "SignUp request container")
public class SignUpRequest {
    @Schema(description = "Login", example = "bibop")
    @NotBlank
    @Size(min = 3, max = 40)
    private String login;

    @Schema(description = "Username", example = "Oleg")
//    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @Schema(description = "Email", example = "oleg1998@gmail.com", format = "email")
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @Schema(description = "Password", example = "qwerty")
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}