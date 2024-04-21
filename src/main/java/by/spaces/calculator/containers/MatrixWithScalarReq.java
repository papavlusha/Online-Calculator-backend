package by.spaces.calculator.containers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MatrixWithScalarReq {
    @NotBlank
    private Object matrixData;
    @NotBlank
    private String scalar;
}
