package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Container with two matrices")
public class MatricesRequest {
    @Schema(description = "First matrix data", example = "2 2\n1 2\n3 4")
    @NotNull
    private Object matrixData;

    @Schema(description = "Second matrix data", example = "[[1, 2], [3, 4]]")
    @NotNull
    private Object matrixData2;

    @Schema(description = "Library to use", example = "Cpp")
    private String lib;
}
