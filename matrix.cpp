#include "matrix.h"
#include <sstream>

Matrix::Matrix() : rows(0), cols(0) {}

Matrix::Matrix(int rows, int cols) : rows(rows), cols(cols) {
    data.resize(rows, std::vector<double>(cols, 0));
}
int Matrix::getRows(){
    return rows;
}
int Matrix::getCols(){
    return cols;
}
std::vector<std::vector<double>> Matrix::getData(){
    return data;
}

Matrix::Matrix(const std::string& inputString) {
    std::stringstream ss(inputString);

    if (!(ss >> rows >> cols)) {
        throw std::runtime_error("Неправильный формат размерности матрицы");
    }

    if (rows <= 0 || cols <= 0) {
        throw std::runtime_error("Неправильный формат размерности матрицы");
    }

    data.resize(rows, std::vector<double>(cols, 0));

    // int numElements = rows * cols;
    int count = 0;

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            std::string elementStr;
            if (!(ss >> elementStr)) {
                throw std::runtime_error("Количество элементов матрицы не соответствует размерности");
            }
            try {
                double element = std::stod(elementStr);
                data[i][j] = element;
                // count++;
            } catch (const std::exception& e) {
                throw std::runtime_error("Неправильный формат элемента матрицы");
            }
        }
    }

    // if (count != numElements) {
    //     throw std::runtime_error("Количество элементов матрицы не соответствует размерности");
    // }
}

Matrix Matrix::operator+(const Matrix& other) const {
    if (rows != other.rows || cols != other.cols) {
        throw std::runtime_error("Нельзя складывать матрицы разных размеров");
    }

    Matrix result(rows, cols);
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            result.data[i][j] = data[i][j] + other.data[i][j];
        }
    }
    return result;
}

Matrix Matrix::operator-(const Matrix& other) const {
    if (rows != other.rows || cols != other.cols) {
        throw std::runtime_error("Нельзя вычитать матрицы разных размеров");
    }

    Matrix result(rows, cols);
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            result.data[i][j] = data[i][j] - other.data[i][j];
        }
    }
    return result;
}

Matrix Matrix::operator*(double scalar) const {
    Matrix result(rows, cols);
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            result.data[i][j] = data[i][j] * scalar;
        }
    }
    return result;
}

Matrix Matrix::operator*(const Matrix& other) const {
    if (cols != other.rows) {
        throw std::runtime_error("Нельзя умножать матрицы так как количество столбцов матрицы А не равно количеству строк матрицы В");
    }

    Matrix result(rows, other.cols);
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < other.cols; j++) {
            for (int k = 0; k < cols; k++) {
                result.data[i][j] += data[i][k] * other.data[k][j];
            }
        }
    }
    return result;
}

void Matrix::print() const {
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            std::cout << data[i][j] << " ";
        }
        std::cout << std::endl;
    }
    std::cout << std::endl;
}

double Matrix::calculateDeterminant(const std::vector<std::vector<double>>& matrix) {
    int size = matrix.size();
    if (size == 1) {
        return matrix[0][0];
    }

    double determinant = 0;
    int sign = 1;

    for (int i = 0; i < size; i++) {
        std::vector<std::vector<double>> subMatrix(size - 1, std::vector<double>(size - 1, 0));

        for (int j = 1; j < size; j++) {
            int k = 0;
            for (int l = 0; l < size; l++) {
                if (l != i) {
                    subMatrix[j - 1][k++] = matrix[j][l];
                }
            }
        }

        determinant += sign * matrix[0][i] * calculateDeterminant(subMatrix);
        sign = -sign;
    }

    return determinant;
}

double Matrix::findDeterminant() {
    if (rows != cols) {
        throw std::runtime_error("Определитель мжно считать только для квадратной матрицы");
    }

    return calculateDeterminant(data);
}

Matrix Matrix::inverseMatrix() const{
    // std::vector<std::vector<double>> matrixData(rows, std::vector<double>(cols));
    // for (int i = 0; i < rows; i++) {
    //     for (int j = 0; j < cols; j++) {
    //         matrixData[i][j] = data[i][j];
    //     }
    // }

    // std::vector<std::vector<double>> inverseData = computeInverse(matrixData);

    return inverse(rows, cols, data);
}
Matrix Matrix::inverse(int rows, int cols, std::vector<std::vector<double>> matrix) const {
    if (rows != cols) {
        throw std::runtime_error("Матрица должна быть квадратной для вычисления обратной матрицы");
    }

    Matrix mtr(matrix);
    double det = mtr.findDeterminant();
    if (det == 0) {
        throw std::runtime_error("Матрица должна быть не вырожденной для вычисления обратной матрицы");
    }

    int n = rows;

    // Создаем расширенную матрицу, где правая часть - единичная матрица
    std::vector<std::vector<double>> augmentedMatrix(n, std::vector<double>(2 * n, 0.0));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            augmentedMatrix[i][j] = matrix[i][j];
        }
        augmentedMatrix[i][i + n] = 1.0;
    }

    // Применяем метод Гаусса-Жордана
    for (int i = 0; i < n; i++) {
        // Поиск главного элемента
        int pivotRow = i;
        for (int j = i + 1; j < n; j++) {
            if (std::abs(augmentedMatrix[j][i]) > std::abs(augmentedMatrix[pivotRow][i])) {
                pivotRow = j;
            }
        }

        // Обмен строк
        if (pivotRow != i) {
            std::swap(augmentedMatrix[pivotRow], augmentedMatrix[i]);
        }

        // Приведение к единичной матрице
        double pivot = augmentedMatrix[i][i];
        if (pivot == 0.0) {
            throw std::runtime_error("Матрица вырожденная, обратная матрица не существует");
        }

        for (int j = 0; j < 2 * n; j++) {
            augmentedMatrix[i][j] /= pivot;
        }

        for (int k = 0; k < n; k++) {
            if (k != i) {
                double factor = augmentedMatrix[k][i];
                for (int j = 0; j < 2 * n; j++) {
                    augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                }
            }
        }
    }

    // Извлекаем обратную матрицу из расширенной матрицы
    std::vector<std::vector<double>> inverseMatrix(n, std::vector<double>(n, 0.0));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            inverseMatrix[i][j] = augmentedMatrix[i][j + n];
        }
    }
    Matrix ans(inverseMatrix);
    return inverseMatrix;

}