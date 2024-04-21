package by.spaces.calculator.service;

import by.spaces.calculator.calculations.Converter;
import by.spaces.calculator.calculations.Matrix;
import by.spaces.calculator.calculations.PrimeNumbersCount;
import by.spaces.calculator.controller.CalculationController;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static by.spaces.calculator.logging.AppLogger.logError;

@Service
public class CalculationService {
    private final Converter converter = new Converter();

    public String convertNumber(String number, int sourceBase, int targetBase) {
        if (sourceBase != 2 && sourceBase != 8 && sourceBase != 10 && sourceBase != 16)
            throw new IllegalArgumentException("Wrong sourceBase");
        try {
            double num = Double.parseDouble(number);
            if (num < 0)
                throw new IllegalArgumentException();
        } catch (Exception e) {
            throw new IllegalArgumentException("The number must be non-negative");
        }
        return converter.convertBase(number, sourceBase, targetBase);
    }

    private boolean validateMatrixData(String inputString) {
        try (Scanner scanner = new Scanner(inputString)) {
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();
            if (rows <= 0 || cols <= 0)
                return false;
            int count = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (!scanner.hasNextDouble())
                        return false;
                    scanner.nextDouble();
                    count++;
                }
            }
            if (scanner.hasNextDouble())
                return false;
            return count == rows * cols;
        } catch (Exception ignored) {
            return false;
        }
    }

    private Matrix createMatrix(Object matrixData) {
        Matrix matrix;
        if (matrixData instanceof String data) {
            if (!validateMatrixData(data))
                throw new IllegalArgumentException("Wrong matrix string");
            matrix = new Matrix(data);
        } else if (matrixData instanceof double[][] data) {
            matrix = new Matrix(data);
        } else if (matrixData instanceof ArrayList<?> data) {
            if (!data.isEmpty() && data.get(0) instanceof ArrayList<?> && !((ArrayList<?>) data.get(0)).isEmpty()) {
                double[][] array;
                if (((ArrayList<?>) data.get(0)).get(0) instanceof Double)
                    array = data.stream()
                            .map(row -> ((ArrayList<Double>) row).stream().mapToDouble(Double::doubleValue).toArray())
                            .toArray(double[][]::new);
                else if (((ArrayList<?>) data.get(0)).get(0) instanceof Integer)
                    array = data.stream()
                            .map(row -> ((ArrayList<Integer>) row).stream().mapToDouble(Integer::doubleValue).toArray())
                            .toArray(double[][]::new);
                else
                    throw new IllegalArgumentException("Wrong matrix array");
                matrix = new Matrix(array);
            } else
                throw new IllegalArgumentException("Wrong matrix array");
        } else {
            throw new IllegalArgumentException("Wrong matrix data");
        }
        return matrix;
    }

    public double getDeterminant(Object matrixData) {
        return createMatrix(matrixData).findDeterminant();
    }

    public double[][] getInverseMatrix(Object matrixData) {
        return createMatrix(matrixData).inverseMatrix().getData();
    }

    public double[][] getTransposeMatrix(Object matrixData) {
        return createMatrix(matrixData).transpose().getData();
    }

    public double[][] matrixMultiplyByScalar(Object matrixData, String scalar) {
        return createMatrix(matrixData).multiply(Double.parseDouble(scalar)).getData();
    }

    public double[][] matrixAddition(Object matrixData, Object matrixData2) {
        return createMatrix(matrixData).add(createMatrix(matrixData2)).getData();
    }

    public double[][] matrixSubtraction(Object matrixData, Object matrixData2) {
        return createMatrix(matrixData).subtract(createMatrix(matrixData2)).getData();
    }

    public double[][] matrixMultiplication(Object matrixData, Object matrixData2) {
        return createMatrix(matrixData).multiply(createMatrix(matrixData2)).getData();
    }

    private void checkRange(Integer start, Integer max) {
        if (max == null || max < 2)
            throw new IllegalArgumentException("Wrong parameter \"max\"");

        if (start != null && (start < 0 || start > max))
            throw new IllegalArgumentException("Wrong parameter \"start\"");

    }

    public String[] calculatePrimes(Integer start, Integer max, Integer threadNum, Integer cycle) throws InterruptedException {
        String[] results;
        checkRange(start, max);
        if (start != null && threadNum != null && cycle != null) {
            try {
                results = new PrimeNumbersCount(start, max, threadNum, cycle).calculatePrimeCount();
            } catch (ExecutionException | InterruptedException e) {
                throw new InterruptedException(e.getMessage());
            }
        } else if (start != null && threadNum != null) {
            try {
                results = new PrimeNumbersCount(start, max, threadNum).calculatePrimeCount();
            } catch (ExecutionException | InterruptedException e) {
                throw new InterruptedException(e.getMessage());
            }
        } else if (start != null) {
            try {
                results = new PrimeNumbersCount(start, max).calculatePrimeCount();
            } catch (ExecutionException | InterruptedException e) {
                throw new InterruptedException(e.getMessage());
            }
        } else {
            try {
                results = new PrimeNumbersCount(max).calculatePrimeCount();
            } catch (ExecutionException | InterruptedException e) {
                throw new InterruptedException(e.getMessage());
            }
        }
        return results;
    }
}
