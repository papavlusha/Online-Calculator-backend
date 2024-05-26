package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Schema(description = "JWT auth response container")
public class JwtAuthenticationResponse {
    @NonNull
    @Schema(description = "Access token", example = "jreu84u3fb9fu3f...")
    private String accessToken;
    @Schema(description = "Token type", example = "Bearer")
    private String tokenType = "Bearer";
}
