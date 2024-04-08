package by.spaces.calculator.controller;

import by.spaces.calculator.calculations.PrimeNumbersCount;
import by.spaces.calculator.containers.PrimeCountRequest;
import by.spaces.calculator.containers.PrimeCountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static by.spaces.calculator.logging.AppLogger.logError;
import static by.spaces.calculator.logging.AppLogger.logInfo;

@Tag(name = "Calculation controller", description = "Controller for processing computational requests")
@RestController
public class CalculationController {
    @Operation(summary = "Calculating the number of prime numbers", description = "This method calculates the number of prime numbers on the interval with given multithreading parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Count primes successfully"),
            @ApiResponse(responseCode = "401", description = "Count failed")
    })
    @PostMapping("/primes")
    public ResponseEntity<PrimeCountResponse> calculatePrimes(@Valid @RequestBody PrimeCountRequest request) {
        Integer max = request.getMax();
        Integer start = request.getStart();
        Integer thredNum = request.getThreads();
        Integer cycle = request.getCycleParam();

        String[] results = new String[2];

        if (max == null){
            logError(CalculationController.class, "Error: required parameter is empty");
            return ResponseEntity
                    .badRequest()
                    .body(new PrimeCountResponse(null, null));
        }

        if (start != null && thredNum != null && cycle != null) {
            try {
                results = new PrimeNumbersCount(start, max, thredNum, cycle).calculatePrimeCount();
            } catch (ExecutionException | InterruptedException e) {
                logError(CalculationController.class, e.getMessage());
                Thread.currentThread().interrupt();
            }
        } else if (start != null && thredNum != null) {
            try {
                results = new PrimeNumbersCount(start, max, thredNum).calculatePrimeCount();
            } catch (ExecutionException | InterruptedException e) {
                logError(CalculationController.class, e.getMessage());
                Thread.currentThread().interrupt();
            }
        } else if (start != null) {
            try {
                results = new PrimeNumbersCount(start, max).calculatePrimeCount();
            } catch (ExecutionException | InterruptedException e) {
                logError(CalculationController.class, e.getMessage());
                Thread.currentThread().interrupt();
            }
        } else {
            try {
                results = new PrimeNumbersCount(max).calculatePrimeCount();
            } catch (ExecutionException | InterruptedException e) {
                logError(CalculationController.class, e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        logInfo(CalculationController.class, "Get result: " + Arrays.toString(results));
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/primes")
                .buildAndExpand().toUri();
        return ResponseEntity
                .created(location)
                .body(new PrimeCountResponse(results[0],results[1]));
    }
}
