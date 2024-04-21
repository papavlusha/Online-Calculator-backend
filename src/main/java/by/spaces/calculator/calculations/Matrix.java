package by.spaces.calculator.calculations;

import java.io.*;

import static by.spaces.calculator.calculations.ExtractLibClass.extractLibrary;

public class Matrix {
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

    public native int getRows();
    public native int getCols();
    public native double[][] getData();
    public native Matrix add(Matrix other);
    public native Matrix subtract(Matrix other);
    public native Matrix multiply(double scalar);
    public native Matrix multiply(Matrix other);
    public native double findDeterminant();
    public native void print();
    public native Matrix inverseMatrix();
    public native Matrix transpose();

    public Matrix() {
        init();
    }

    public Matrix(int rows, int cols) {
        init(rows, cols);
    }

    public Matrix(String inputString) {
        init(inputString);
    }

    public Matrix(double[][] matrixData) {
        init(matrixData);
    }

}