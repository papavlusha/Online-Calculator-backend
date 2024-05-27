package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Convert request container")
public class ConvertRequest {
    @Schema(description = "Number system", example = "10")
    @NotBlank
    private String sourceBase;
    @Schema(description = "Number to convert", example = "25")
    @NotBlank
    private String number;
    @Schema(description = "Library to use", example = "Cpp")
    private String lib;
}
