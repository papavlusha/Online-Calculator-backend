package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Convert response container")
public class ConvertResponse {
    @Schema(description = "Binary number", example = "11001")
    private String binaryNumber;
    @Schema(description = "Decimal number", example = "25")
    private String decimalNumber;
    @Schema(description = "Octal number", example = "31")
    private String octalNumber;
    @Schema(description = "Hexadecimal number", example = "19")
    private String hexadecimalNumber;
}
