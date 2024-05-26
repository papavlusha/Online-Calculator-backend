package by.spaces.calculator.controller;

import by.spaces.calculator.containers.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import by.spaces.calculator.service.CalculationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import static by.spaces.calculator.logging.AppLogger.logError;
import static by.spaces.calculator.logging.AppLogger.logInfo;

@Tag(name = "Calculation controller", description = "Controller for processing computational requests")
@RestController
public class CalculationController {
    @Operation(summary = "Calculating the number of prime numbers", description = "This method calculates the number of prime numbers on the interval with given multithreading parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count primes successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrimeCountResponse.class))),
            @ApiResponse(responseCode = "400", description = "Count failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/primes")
    public ResponseEntity<Object> calculatePrimes(@Valid @RequestBody PrimeCountRequest request) {
        Integer max = request.getMax();
        Integer start = request.getStart();
        Integer threadNum = request.getThreads();
        Integer cycle = request.getCycleParam();
        try{
            String[] results = service.calculatePrimes(start, max, threadNum, cycle);
            logInfo(CalculationController.class, "Get primes result: " + Arrays.toString(results));
            return ResponseEntity.ok(new PrimeCountResponse(results[0], results[1]));
        } catch (Exception e){
            return handleException("Prime calculation failed: ", e);
        }
    }

    private final CalculationService service = new CalculationService();

    @Operation(summary = "Number conversion", description = "This method Convert numbers to binary, decimal, octal and hexadecimal number systems.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convert number successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConvertResponse.class))),
            @ApiResponse(responseCode = "400", description = "Conversion failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/converter")
    public ResponseEntity<Object> convertNumber(@Valid @RequestBody ConvertRequest convertRequest) {
        try{
            int sourceBase = Integer.parseInt(convertRequest.getSourceBase());
            String number = convertRequest.getNumber();
            String bin = service.convertNumber(number, sourceBase, 2);
            String dec = service.convertNumber(number, sourceBase, 10);
            String oct = service.convertNumber(number, sourceBase, 8);
            String hex = service.convertNumber(number, sourceBase, 16);
            logInfo(CalculationController.class, "Get convert result: bin - " + bin
                    + ", dec - " + dec
                    + ", oct - " + oct
                    + ", hex - " + hex);
            ConvertResponse response = new ConvertResponse(bin, dec, oct, hex);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse("Conversion failed: " + e.getMessage());
            logError(CalculationController.class, errorResponse.getError());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @Operation(summary = "Matrix determinant", description = "Calculating the determinant of a matrix.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The determinant has been calculated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "25"))),
            @ApiResponse(responseCode = "400", description = "Calculation of the determinant failed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Determinant calculation failed: Wrong matrix string")))
    })
    @PostMapping("/matrix/determinant")
    public ResponseEntity<String> getMatrixDeterminant(@Valid @RequestBody
                                                           @Schema(example = "[[1,2],[3,4]]")
                                                           Object requestData) {
        try{
            String determinant = String.valueOf(service.getDeterminant(requestData));
            logInfo(CalculationController.class, "Get determinant result: " + determinant);
            return ResponseEntity.ok(determinant);
        } catch (Exception e){
            String error = "Determinant calculation failed: " + e.getMessage();
            logError(CalculationController.class, error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @Operation(summary = "Inverse matrix", description = "Inverse matrix calculation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inverse matrix calculated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatrixResponse.class))),
            @ApiResponse(responseCode = "400", description = "Inverse matrix calculation failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/matrix/inverse")
    public ResponseEntity<Object> inverseMatrix(@Valid @RequestBody
                                                            @Schema(example = "[[1,2],[3,4]]")
                                                            Object requestData) {
        try{
            double[][] matrix = service.getInverseMatrix(requestData);
            logInfo(CalculationController.class, "Get inverse result: " + arrayToString(matrix));
            return ResponseEntity.ok(new MatrixResponse(matrix));
        } catch (Exception e){
            return handleException("Inverse matrix calculation failed: ", e);
        }
    }

    @Operation(summary = "Transpose matrix", description = "Transpose matrix calculation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transpose matrix calculated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatrixResponse.class))),
            @ApiResponse(responseCode = "400", description = "Transpose matrix calculation failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/matrix/transpose")
    public ResponseEntity<Object> transposeMatrix(@Valid @RequestBody
                                                              @Schema(example = "[[1,2],[3,4]]")
                                                              Object requestData) {
        try{
            double[][] matrix = service.getTransposeMatrix(requestData);
            logInfo(CalculationController.class, "Get transpose result: " + arrayToString(matrix));
            return ResponseEntity.ok(new MatrixResponse(matrix));
        } catch (Exception e){
            return handleException("Transpose matrix calculation failed: ", e);
        }
    }

    @Operation(summary = "Multiplication of a matrix by a scalar", description = "Calculating a matrix by multiplying the original matrix by a scalar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matrix multiplied by a scalar successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatrixResponse.class))),
            @ApiResponse(responseCode = "400", description = "Matrix multiplication by scalar failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/matrix/multiply_by_scalar")
    public ResponseEntity<Object> multiplyMatrixByScalar(@Valid @RequestBody MatrixWithScalarReq requestData) {
        try{
            double[][] matrix = service.matrixMultiplyByScalar(requestData.getMatrixData(), requestData.getScalar());
            logInfo(CalculationController.class, "Get scalar multiply result: " + arrayToString(matrix));
            return ResponseEntity.ok(new MatrixResponse(matrix));
        } catch (Exception e){
            return handleException("Matrix multiplication by scalar failed: ", e);
        }
    }

    @Operation(summary = "Matrix addition", description = "Adding two matrices of the same size.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matrix addition was successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatrixResponse.class))),
            @ApiResponse(responseCode = "400", description = "Matrix addition failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/matrix/add")
    public ResponseEntity<Object> addMatrix(@Valid @RequestBody MatricesRequest requestData) {
        try{
            double[][] matrix = service.matrixAddition(requestData.getMatrixData(), requestData.getMatrixData2());
            logInfo(CalculationController.class, "Get matrix addition result: " + arrayToString(matrix));
            return ResponseEntity.ok(new MatrixResponse(matrix));
        } catch (Exception e){
            return handleException("Matrix addition failed: ", e);
        }
    }

    @Operation(summary = "Matrix subtraction", description = "Subtraction two matrices of the same size.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matrix subtraction was successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatrixResponse.class))),
            @ApiResponse(responseCode = "400", description = "Matrix subtraction failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/matrix/subtract")
    public ResponseEntity<Object> subtractMatrix(@Valid @RequestBody MatricesRequest requestData) {
        try{
            double[][] matrix = service.matrixSubtraction(requestData.getMatrixData(), requestData.getMatrixData2());
            logInfo(CalculationController.class, "Get matrix subtraction result: " + arrayToString(matrix));
            return ResponseEntity.ok(new MatrixResponse(matrix));
        } catch (Exception e){
            return handleException("Matrix subtraction failed: ", e);
        }
    }

    @Operation(summary = "Matrix multiplication", description = "Multiplication two matrices.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matrix multiplication was successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatrixResponse.class))),
            @ApiResponse(responseCode = "400", description = "Matrix multiplication failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/matrix/multiply")
    public ResponseEntity<Object> multiplyMatrix(@Valid @RequestBody MatricesRequest requestData) {
        try{
            double[][] matrix = service.matrixMultiplication(requestData.getMatrixData(), requestData.getMatrixData2());
            logInfo(CalculationController.class, "Get matrix multiplication result: " + arrayToString(matrix));
            return ResponseEntity.ok(new MatrixResponse(matrix));
        } catch (Exception e){
            return handleException("Matrix multiplication failed: ", e);
        }
    }

    private ResponseEntity<Object> handleException(String err, Exception e) {
        if (e instanceof InterruptedException)
            Thread.currentThread().interrupt();
        ErrorResponse errorResponse = new ErrorResponse(err + e.getMessage());
        logError(CalculationController.class, errorResponse.getError());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    public static String arrayToString(double[][] matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < matrix.length; i++) {
            sb.append("[");
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    sb.append(", ");
                }
            }
            if (i < matrix.length - 1) {
                sb.append("], ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
