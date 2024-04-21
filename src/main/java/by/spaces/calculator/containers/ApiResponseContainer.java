package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "API response container")
public class ApiResponseContainer {
    @Schema(description = "Success flag", example = "true")
    private Boolean success;
    @Schema(description = "Result message", example = "User registered successfully")
    private String message;
}