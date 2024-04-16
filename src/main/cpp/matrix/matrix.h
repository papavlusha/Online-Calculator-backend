#ifndef MATRIX_H
#define MATRIX_H

#include <vector>
#include <iostream>

class Matrix {
private:
    std::vector<std::vector<double>> data;
    int rows;
    int cols;

public:
    Matrix();
    Matrix(int rows, int cols);
    Matrix(const std::string& inputString);
    Matrix(const std::vector<std::vector<double>>& matrixData) : data(matrixData), rows(matrixData.size()), cols(matrixData[0].size()) {};
    int getRows();
    int getCols();
    std::vector<std::vector<double>> getData();
    Matrix operator+(const Matrix& other) const;
    Matrix operator-(const Matrix& other) const;
    Matrix operator*(double scalar) const;
    Matrix operator*(const Matrix& other) const;
    double findDeterminant();
    void print() const;
    Matrix inverseMatrix() const;
    Matrix transpose();


private:
Matrix inverse(int, int, std::vector<std::vector<double>> matrix) const;

double calculateDeterminant(const std::vector<std::vector<double>>& matrix);

};

#endif  // MATRIX_H