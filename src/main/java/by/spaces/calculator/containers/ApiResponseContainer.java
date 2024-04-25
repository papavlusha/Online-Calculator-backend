package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "API response container")
public class ApiResponseContainer {
    @Schema(description = "Success flag", examples = {"true", "false"})
    private Boolean success;
    @Schema(description = "Result message", example = "User authorization result")
    private String message;
}