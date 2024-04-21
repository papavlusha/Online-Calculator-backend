package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Matrix response container")
public class MatrixResponse {
    @Schema(description = "Data of the obtained matrix", example = "[[1, 2], [3, 4]]")
    private double[][] matrix;
    @Schema(description = "Error message", example = "null")
    private String error;
}
