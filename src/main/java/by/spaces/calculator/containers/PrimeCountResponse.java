package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "PrimeCount response container")
public class PrimeCountResponse {
    @Schema(description = "Number of prime numbers in the range", example = "664579")
    private String result;
    @Schema(description = "Calculation time of prime numbers in the range in seconds", example = "0.113")
    private String time;
}
