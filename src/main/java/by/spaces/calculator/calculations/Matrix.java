package by.spaces.calculator.calculations;

public interface Matrix {
    int getRows();
    int getCols();
    double[][] getData();
    Matrix addMatrix(Matrix other);
    Matrix subtractMatrix(Matrix other);
    Matrix multiplyMatrix(double scalar);
    Matrix multiplyMatrix(Matrix other);
    double findDeterminant();
    void print();
    Matrix inverseMatrix();
    Matrix transpose();
}
