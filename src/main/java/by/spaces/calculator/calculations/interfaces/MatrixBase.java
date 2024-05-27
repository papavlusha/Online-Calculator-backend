package by.spaces.calculator.calculations.interfaces;

public interface MatrixBase {
    int getRows();
    int getCols();
    double[][] getData();
    MatrixBase addMatrix(MatrixBase other);
    MatrixBase subtractMatrix(MatrixBase other);
    MatrixBase multiplyMatrix(double scalar);
    MatrixBase multiplyMatrix(MatrixBase other);
    double findDeterminant();
    void print();
    MatrixBase inverseMatrix();
    MatrixBase transpose();
}
