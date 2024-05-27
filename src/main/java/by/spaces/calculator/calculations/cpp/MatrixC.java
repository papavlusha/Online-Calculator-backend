package by.spaces.calculator.calculations.cpp;

import by.spaces.calculator.calculations.Matrix;

import java.io.IOException;

import static by.spaces.calculator.calculations.ExtractLibClass.extractLibrary;

public class MatrixC implements Matrix {
    static {
        try {
            System.load(extractLibrary("Matrix"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private long nativeHandle;

    private native void init();
    private native void init(int rows, int cols);
    private native void init(String inputString);
    private native void init(double[][] matrixData);

    @Override
    public Matrix addMatrix(Matrix other) {
        return this.add((MatrixC) other);
    }

    @Override
    public Matrix subtractMatrix(Matrix other) {
        return this.subtract((MatrixC) other);
    }

    @Override
    public Matrix multiplyMatrix(double scalar) {
        return this.multiply(scalar);
    }

    @Override
    public Matrix multiplyMatrix(Matrix other) {
        return this.multiply((MatrixC) other);
    }

    public native int getRows();
    public native int getCols();
    public native double[][] getData();

    public native MatrixC add(MatrixC other);
    public native MatrixC subtract(MatrixC other);
    public native MatrixC multiply(double scalar);
    public native MatrixC multiply(MatrixC other);
    public native double findDeterminant();
    public native void print();
    public native MatrixC inverseMatrix();
    public native MatrixC transpose();

    public MatrixC() {
        init();
    }

    public MatrixC(int rows, int cols) {
        init(rows, cols);
    }

    public MatrixC(String inputString) {
        init(inputString);
    }

    public MatrixC(double[][] matrixData) {
        init(matrixData);
    }

}