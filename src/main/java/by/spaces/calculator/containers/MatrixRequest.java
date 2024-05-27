package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Matrix request container")
public class MatrixRequest {
    @Schema(description = "Matrix data", example = "[[1,2],[3,4]]")
    private Object matrixData;
    @Schema(description = "Library to use", example = "Cpp")
    private String lib;
}
