package by.spaces.calculator.containers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConvertRequest {
    @NotBlank
    private String sourceBase;
    @NotBlank
    private String number;
}
