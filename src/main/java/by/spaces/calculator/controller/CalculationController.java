package by.spaces.calculator.controller;

import by.spaces.calculator.calculations.PrimeNumbersCount;
import by.spaces.calculator.containers.PrimeCountRequest;
import by.spaces.calculator.containers.PrimeCountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import by.spaces.calculator.containers.ConvertRequest;
import by.spaces.calculator.containers.ConvertResponse;
import by.spaces.calculator.containers.MatricesRequest;
import by.spaces.calculator.containers.MatrixWithScalarReq;
import by.spaces.calculator.service.CalculationService;
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
        Integer threadNum = request.getThreads();
        Integer cycle = request.getCycleParam();

        String[] results = new String[2];

        if (max == null) {
            logError(CalculationController.class, "Error: required parameter is empty");
            return ResponseEntity
                    .badRequest()
                    .body(new PrimeCountResponse(null, null));
        }

        if (start != null && threadNum != null && cycle != null) {
            try {
                results = new PrimeNumbersCount(start, max, threadNum, cycle).calculatePrimeCount();
            } catch (ExecutionException | InterruptedException e) {
                logError(CalculationController.class, e.getMessage());
                Thread.currentThread().interrupt();
            }
        } else if (start != null && threadNum != null) {
            try {
                results = new PrimeNumbersCount(start, max, threadNum).calculatePrimeCount();
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
                .body(new PrimeCountResponse(results[0], results[1]));
    }

    private final CalculationService service = new CalculationService();

    @Operation(summary = "Number conversion", description = "This method Convert numbers to binary, decimal, octal and hexadecimal number systems.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convert number successfully"),
            @ApiResponse(responseCode = "403", description = "Conversion failed")
    })
    @PostMapping("/converter")
    public ResponseEntity<ConvertResponse> convertNumber(@Valid @RequestBody ConvertRequest convertRequest) {
        int sourceBase = Integer.parseInt(convertRequest.getSourceBase());
        String number = convertRequest.getNumber();
        String bin = service.convertNumber(number, sourceBase, 2);
        String dec = service.convertNumber(number, sourceBase, 10);
        String oct = service.convertNumber(number, sourceBase, 8);
        String hex = service.convertNumber(number, sourceBase, 16);
        logInfo(CalculationController.class, "Get result: bin - " + bin
                + ", dec - " + dec
                + ", oct - " + oct
                + ", hex - " + hex);
        ConvertResponse response = new ConvertResponse(bin, dec, oct, hex);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Matrix determinant", description = "Calculating the determinant of a matrix.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The determinant has been calculated successfully"),
            @ApiResponse(responseCode = "403", description = "Calculation of the determinant failed")
    })
    @PostMapping("/matrix/determinant")
    public ResponseEntity<String> getMatrixDeterminant(@Valid @RequestBody Object requestData) {
        String determinant = String.valueOf(service.getDeterminant(requestData));
        return ResponseEntity.ok(determinant);
    }

    @Operation(summary = "Inverse matrix", description = "Inverse matrix calculation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inverse matrix calculated successfully"),
            @ApiResponse(responseCode = "403", description = "Inverse matrix calculation failed")
    })
    @PostMapping("/matrix/inverse")
    public ResponseEntity<double[][]> inverseMatrix(@Valid @RequestBody Object requestData) {
        double[][] matrix = service.getInverseMatrix(requestData);
        return ResponseEntity.ok(matrix);
    }

    @Operation(summary = "Transpose matrix", description = "Transpose matrix calculation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transpose matrix calculated successfully"),
            @ApiResponse(responseCode = "403", description = "Transpose matrix calculation failed")
    })
    @PostMapping("/matrix/transpose")
    public ResponseEntity<double[][]> transposeMatrix(@Valid @RequestBody Object requestData) {
        double[][] matrix = service.getTransposeMatrix(requestData);
        return ResponseEntity.ok(matrix);
    }

    @Operation(summary = "Multiplication of a matrix by a scalar", description = "Calculating a matrix by multiplying the original matrix by a scalar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matrix multiplied by a scalar successfully"),
            @ApiResponse(responseCode = "403", description = "Matrix multiplication by scalar failed")
    })
    @PostMapping("/matrix/multiply_by_scalar")
    public ResponseEntity<double[][]> multiplyMatrixByScalar(@Valid @RequestBody MatrixWithScalarReq requestData) {
        double[][] matrix = service.matrixMultiplyByScalar(requestData.getMatrixData(), requestData.getScalar());
        return ResponseEntity.ok(matrix);
    }

    @Operation(summary = "Matrix addition", description = "Adding two matrices of the same size.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matrix addition was successful"),
            @ApiResponse(responseCode = "403", description = "Matrix addition failed")
    })
    @PostMapping("/matrix/add")
    public ResponseEntity<double[][]> addMatrix(@Valid @RequestBody MatricesRequest requestData) {
        double[][] matrix = service.matrixAddition(requestData.getMatrixData(), requestData.getMatrixData2());
        return ResponseEntity.ok(matrix);
    }

    @Operation(summary = "Matrix subtraction", description = "Subtraction two matrices of the same size.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matrix subtraction was successful"),
            @ApiResponse(responseCode = "403", description = "Matrix subtraction failed")
    })
    @PostMapping("/matrix/subtract")
    public ResponseEntity<double[][]> subtractMatrix(@Valid @RequestBody MatricesRequest requestData) {
        double[][] matrix = service.matrixSubtraction(requestData.getMatrixData(), requestData.getMatrixData2());
        return ResponseEntity.ok(matrix);
    }

    @Operation(summary = "Matrix multiplication", description = "Multiplication two matrices.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matrix multiplication was successful"),
            @ApiResponse(responseCode = "403", description = "Matrix multiplication failed")
    })
    @PostMapping("/matrix/multiply")
    public ResponseEntity<double[][]> multiplyMatrix(@Valid @RequestBody MatricesRequest requestData) {
        double[][] matrix = service.matrixMultiplication(requestData.getMatrixData(), requestData.getMatrixData2());
        return ResponseEntity.ok(matrix);
    }
}
