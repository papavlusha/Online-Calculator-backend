package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Container with matrix and scalar")
public class MatrixWithScalarReq {
    @Schema(description = "Matrix data", example = "2 2\n1 2\n3 4")
    @NotBlank
    private Object matrixData;

    @Schema(description = "Scalar", example = "5")
    @NotBlank
    private String scalar;
}
