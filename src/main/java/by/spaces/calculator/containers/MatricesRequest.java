package by.spaces.calculator.containers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MatricesRequest {
    @NotBlank
    private Object matrixData;
    @NotBlank
    private Object matrixData2;
}
