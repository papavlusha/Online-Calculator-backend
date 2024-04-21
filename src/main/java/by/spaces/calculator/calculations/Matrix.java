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
    private native double[][] getData();
    private native Matrix add(Matrix other);
    private native Matrix subtract(Matrix other);
    private native Matrix multiply(double scalar);
    private native Matrix multiply(Matrix other);
    private native double findDeterminant();
    private native void print();
    private native Matrix inverseMatrix();
    private native Matrix transpose();

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