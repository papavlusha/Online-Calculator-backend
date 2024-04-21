package by.spaces.calculator.controller;

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

@RestController
public class CalculationController {
    private final CalculationService service = new CalculationService();
    @PostMapping("/converter")
    public ResponseEntity<ConvertResponse> convertNumber(@Valid @RequestBody ConvertRequest convertRequest) {
        int sourceBase = Integer.parseInt(convertRequest.getSourceBase());
        String number = convertRequest.getNumber();
        String bin = service.convertNumber(number, sourceBase, 2);
        String dec = service.convertNumber(number, sourceBase, 10);
        String oct = service.convertNumber(number, sourceBase, 8);
        String hex = service.convertNumber(number, sourceBase, 16);

        ConvertResponse response = new ConvertResponse(bin, dec, oct, hex);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/matrix/determinant")
    public ResponseEntity<String> getMatrixDeterminant(@Valid @RequestBody Object requestData) {
        String determinant = String.valueOf(service.getDeterminant(requestData));
        return ResponseEntity.ok(determinant);
    }

    @PostMapping("/matrix/inverse")
    public ResponseEntity<double[][]> inverseMatrix(@Valid @RequestBody Object requestData) {
        double[][] matrix = service.getInverseMatrix(requestData);
        return ResponseEntity.ok(matrix);
    }

    @PostMapping("/matrix/transpose")
    public ResponseEntity<double[][]> transposeMatrix(@Valid @RequestBody Object requestData) {
        double[][] matrix = service.getTransposeMatrix(requestData);
        return ResponseEntity.ok(matrix);
    }

    @PostMapping("/matrix/multiply_by_scalar")
    public ResponseEntity<double[][]> multiplyMatrixByScalar(@Valid @RequestBody MatrixWithScalarReq requestData) {
        double[][] matrix = service.matrixMultiplyByScalar(requestData.getMatrixData(), requestData.getScalar());
        return ResponseEntity.ok(matrix);
    }

    @PostMapping("/matrix/add")
    public ResponseEntity<double[][]> addMatrix(@Valid @RequestBody MatricesRequest requestData) {
        double[][] matrix = service.matrixAddition(requestData.getMatrixData(), requestData.getMatrixData2());
        return ResponseEntity.ok(matrix);
    }

    @PostMapping("/matrix/subtract")
    public ResponseEntity<double[][]> subtractMatrix(@Valid @RequestBody MatricesRequest requestData) {
        double[][] matrix = service.matrixSubtraction(requestData.getMatrixData(), requestData.getMatrixData2());
        return ResponseEntity.ok(matrix);
    }

    @PostMapping("/matrix/multiply")
    public ResponseEntity<double[][]> multiplyMatrix(@Valid @RequestBody MatricesRequest requestData) {
        double[][] matrix = service.matrixMultiplication(requestData.getMatrixData(), requestData.getMatrixData2());
        return ResponseEntity.ok(matrix);
    }
}
