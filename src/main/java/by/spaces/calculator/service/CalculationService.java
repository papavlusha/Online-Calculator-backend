package by.spaces.calculator.service;

import by.spaces.calculator.calculations.Converter;
import by.spaces.calculator.calculations.Matrix;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {
    private final Converter converter = new Converter();
    public String convertNumber(String number, int sourceBase, int targetBase){
        if (sourceBase != 2 && sourceBase != 8 && sourceBase != 10 && sourceBase != 16)
            throw new IllegalArgumentException("Wrong sourceBase");
        try{
            double num = Double.parseDouble(number);
            if (num < 0)
                throw new IllegalArgumentException();
        }catch (Exception e){
            throw new IllegalArgumentException("The number must be non-negative");
        }
        return converter.convertBase(number, sourceBase, targetBase);
    }

    private Matrix createMatrix(Object matrixData){
        Matrix matrix;
        if (matrixData instanceof String data) {
            matrix = new Matrix(data);
        } else if (matrixData instanceof double[][] data) {
            matrix = new Matrix(data);
        } else {
            throw new IllegalArgumentException();
        }
        return matrix;
    }

    public double getDeterminant(Object matrixData){
        return createMatrix(matrixData).findDeterminant();
    }

    public double[][] getInverseMatrix(Object matrixData){
        return createMatrix(matrixData).inverseMatrix().getData();
    }

    public double[][] getTransposeMatrix(Object matrixData){
        return createMatrix(matrixData).transpose().getData();
    }

    public double[][] matrixMultiplyByScalar(Object matrixData, String scalar){
        return createMatrix(matrixData).multiply(Double.parseDouble(scalar)).getData();
    }

    public double[][] matrixAddition(Object matrixData, Object matrixData2){
        return createMatrix(matrixData).add(createMatrix(matrixData2)).getData();
    }

    public double[][] matrixSubtraction(Object matrixData, Object matrixData2){
        return createMatrix(matrixData).subtract(createMatrix(matrixData2)).getData();
    }

    public double[][] matrixMultiplication(Object matrixData, Object matrixData2){
        return createMatrix(matrixData).multiply(createMatrix(matrixData2)).getData();
    }
}
