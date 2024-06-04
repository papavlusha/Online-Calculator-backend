package by.spaces.calculator.calculations;

import by.spaces.calculator.calculations.interfaces.MatrixBase;

public class Matrix implements MatrixBase {
    private long nativeHandle;

    private native void init();
    private native void init(int rows, int cols);
    private native void init(String inputString);
    private native void init(double[][] matrixData);

    @Override
    public MatrixBase addMatrix(MatrixBase other) {
        return this.add((Matrix) other);
    }

    @Override
    public MatrixBase subtractMatrix(MatrixBase other) {
        return this.subtract((Matrix) other);
    }

    @Override
    public MatrixBase multiplyMatrix(double scalar) {
        return this.multiply(scalar);
    }

    @Override
    public MatrixBase multiplyMatrix(MatrixBase other) {
        return this.multiply((Matrix) other);
    }

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