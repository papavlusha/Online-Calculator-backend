package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Error response container")
public class ErrorResponse {
    @Schema(description = "Error message", example = "Calculation failed: Wrong data")
    private String error;
}
