package by.spaces.calculator.service;

import by.spaces.calculator.calculations.Converter;
import by.spaces.calculator.calculations.Matrix;
import by.spaces.calculator.calculations.PrimeNumbersCount;
import by.spaces.calculator.calculations.interfaces.ConverterBase;
import by.spaces.calculator.calculations.interfaces.MatrixBase;
import by.spaces.calculator.calculations.java.ConverterJ;
import by.spaces.calculator.calculations.java.MatrixJ;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Service
public class CalculationService {
    private ConverterBase getConverter(String lib){
        if (lib == null || lib.isEmpty() || lib.equalsIgnoreCase("cpp"))
            return new Converter();
        else
            return new ConverterJ();
    }

    public String convertNumber(String number, int sourceBase, int targetBase, String lib) {
        if (sourceBase != 2 && sourceBase != 8 && sourceBase != 10 && sourceBase != 16)
            throw new IllegalArgumentException("Wrong sourceBase");
        try {
            double num = Double.parseDouble(number);
            if (num < 0)
                throw new IllegalArgumentException();
        } catch (Exception e) {
            throw new IllegalArgumentException("The number must be non-negative");
        }
        return getConverter(lib).convertBase(number, sourceBase, targetBase);
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

    private MatrixBase getMatrix(String lib, String data){
        if (lib == null || lib.isEmpty() || lib.equalsIgnoreCase("cpp"))
            return new Matrix(data);
        else
            return new MatrixJ(data);
    }

    private MatrixBase getMatrix(String lib, double[][] data){
        if (lib == null || lib.isEmpty() || lib.equalsIgnoreCase("cpp"))
            return new Matrix(data);
        else
            return new MatrixJ(data);
    }

    private MatrixBase createMatrix(Object matrixData, String lib) {
        MatrixBase matrix;
        if (matrixData instanceof String data) {
            if (!validateMatrixData(data))
                throw new IllegalArgumentException("Wrong matrix string");
            matrix = getMatrix(lib, data);
        } else if (matrixData instanceof double[][] data) {
            matrix = getMatrix(lib, data);
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
                matrix = getMatrix(lib, array);
            } else
                throw new IllegalArgumentException("Wrong matrix array");
        } else {
            throw new IllegalArgumentException("Wrong matrix data");
        }
        return matrix;
    }

    public double getDeterminant(Object matrixData, String lib) {
        return createMatrix(matrixData, lib).findDeterminant();
    }

    public double[][] getInverseMatrix(Object matrixData, String lib) {
        return createMatrix(matrixData, lib).inverseMatrix().getData();
    }

    public double[][] getTransposeMatrix(Object matrixData, String lib) {
        return createMatrix(matrixData, lib).transpose().getData();
    }

    public double[][] matrixMultiplyByScalar(Object matrixData, String scalar, String lib) {
        return createMatrix(matrixData, lib).multiplyMatrix(Double.parseDouble(scalar)).getData();
    }

    public double[][] matrixAddition(Object matrixData, Object matrixData2, String lib) {
        return createMatrix(matrixData, lib).addMatrix(createMatrix(matrixData2, lib)).getData();
    }

    public double[][] matrixSubtraction(Object matrixData, Object matrixData2, String lib) {
        return createMatrix(matrixData, lib).subtractMatrix(createMatrix(matrixData2, lib)).getData();
    }

    public double[][] matrixMultiplication(Object matrixData, Object matrixData2, String lib) {
        return createMatrix(matrixData, lib).multiplyMatrix(createMatrix(matrixData2, lib)).getData();
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
