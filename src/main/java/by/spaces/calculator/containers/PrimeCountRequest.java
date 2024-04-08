package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "PrimeCount request container")
public class PrimeCountRequest {
    private Integer start;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer max;
    private Integer threads;
    private Integer cycleParam;
}
