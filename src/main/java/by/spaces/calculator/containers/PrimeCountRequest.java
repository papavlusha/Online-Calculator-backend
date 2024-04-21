package by.spaces.calculator.containers;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "PrimeCount request container")
public class PrimeCountRequest {
    @Schema(description = "Range start", example = "2")
    private Integer start;
    @Schema(description = "Range end", example = "10000000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer max;
    @Schema(description = "Number of threads", example = "4")
    private Integer threads;
    @Schema(description = "Cyclic data partitioning range for threads", example = "10000")
    private Integer cycleParam;
}
