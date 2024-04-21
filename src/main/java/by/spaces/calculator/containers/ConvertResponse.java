package by.spaces.calculator.containers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConvertResponse {
    private String binaryNumber;
    private String decimalNumber;
    private String octalNumber;
    private String hexadecimalNumber;
}
