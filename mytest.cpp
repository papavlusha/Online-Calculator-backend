#include <iostream>
#include <cassert>
#include "matrix.h"

void testConstructor() {
    // Тест 1: Создание пустой матрицы
    Matrix matrix1;
    assert(matrix1.getRows() == 0);
    assert(matrix1.getCols() == 0);

    // Тест 2: Создание матрицы размером 2x3
    Matrix matrix2(2, 3);
    assert(matrix2.getRows() == 2);
    assert(matrix2.getCols() == 3);
    assert(matrix2.getData() == std::vector<std::vector<double>>(2, std::vector<double>(3, 0)));

    // Тест 3: Создание матрицы из строки
    std::string inputString = "2 3 1 2 3 4 5 6";
    Matrix matrix3(inputString);
    assert(matrix3.getRows() == 2);
    assert(matrix3.getCols() == 3);
    assert(matrix3.getData() == std::vector<std::vector<double>>({{1, 2, 3}, {4, 5, 6}}));

    // Тест 4: Ошибка неверного количества элементов
    std::string invalidInputString = "2 3 1 2 3 4";
    try {
        Matrix matrix4(invalidInputString);
        assert(false); // Можно использовать, если ожидается исключение
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Количество элементов матрицы не соответствует размерности";
        assert(error.what() == expectedError);
    }
    // Тест 5: Ошибка дробных размеров матрицы
    invalidInputString = "2.1 3.2 1 2 3 4 5 6";
    try {
        Matrix matrix5(invalidInputString);
        assert(false); // Можно использовать, если ожидается исключение
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Неправильный формат размерности матрицы";
        // std::cout << error.what() << "\n";
        assert(error.what() == expectedError);
    }
    // Тест 6: Ошибка ввода не чисел
    invalidInputString = "2 3 1 2 а б 5 6";
    try {
        Matrix matrix6(invalidInputString);
        assert(false); // Можно использовать, если ожидается исключение
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Неправильный формат элемента матрицы";
        // std::cout << error.what() << "\n";
        assert(error.what() == expectedError);
    }
    // Тест 7: Ошибка букв в размерах матрицы
    invalidInputString = "ф 3 1 2 3 4 5 6";
    try {
        Matrix matrix7(invalidInputString);
        assert(false); // Можно использовать, если ожидается исключение
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Неправильный формат размерности матрицы";
        // std::cout << error.what() << "\n";
        assert(error.what() == expectedError);
    }
     // Тест 8: Ошибка отрицательных размеров матрицы
    invalidInputString = "-2 3 1 2 3 4 5 6";
    try {
        Matrix matrix8(invalidInputString);
        assert(false); // Можно использовать, если ожидается исключение
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Неправильный формат размерности матрицы";
        // std::cout << error.what() << "\n";
        assert(error.what() == expectedError);
    }
    // Тест 9: Создание матрицы размером 2x3 из вектора
    std::vector<std::vector<double>> testVec9({{1, 2, 3}, {4, 5, 6}});
    Matrix matrix9(testVec9);
    assert(matrix9.getRows() == 2);
    assert(matrix9.getCols() == 3);
    assert(matrix9.getData() == std::vector<std::vector<double>>({{1, 2, 3}, {4, 5, 6}}));

}

void testAddition() {
    // Тест 1: Сложение матриц одинакового размера
    Matrix matrix1("2 2 1 2 3 4");
    Matrix matrix2("2 2 5 6 7 8");
    Matrix result1 = matrix1 + matrix2;
    Matrix expected1("2 2 6 8 10 12");
    assert(result1.getRows() == expected1.getRows());
    assert(result1.getCols() == expected1.getCols());
    assert(result1.getData() == expected1.getData());

    // Тест 2: Ошибка неправильных размеров матриц для сложения
    Matrix matrix3("2 3 1 2 3 4 5 6");
    Matrix matrix4("3 2 1 2 3 4 5 6");
    try {
        Matrix result2 = matrix3 + matrix4;
        assert(false); // Можно использовать, если ожидается исключение
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Нельзя складывать матрицы разных размеров";
        assert(error.what() == expectedError);
    }
}

void testSubtraction() {
    // Тест 1: Вычитание матриц одинакового размера
    Matrix matrix1("2 2 5 6.1 7 8");
    Matrix matrix2("2 2 1 2 3 4");
    Matrix result1 = matrix1 - matrix2;
    Matrix expected1("2 2 4 4.1 4 4");
    assert(result1.getRows() == expected1.getRows());
    assert(result1.getCols() == expected1.getCols());
    assert(result1.getData() == expected1.getData());

    // Тест 2: Ошибка неправильных размеров матриц для вычитания
    Matrix matrix3("2 3 1 2 3 4 5 6");
    Matrix matrix4("3 2 1 2 3 4 5 6");
    try {
        Matrix result2 = matrix3 - matrix4;
        assert(false); // Можно использовать, если ожидается исключение
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Нельзя вычитать матрицы разных размеров";
        assert(error.what() == expectedError);
    }
}

void testScalarMultiplication() {
    // Тест 1: Умножение матрицы на скаляр
    Matrix matrix1("2 2 1 2 3 4");
    Matrix result1 = matrix1 * 2;
    Matrix expected1("2 2 2 4 6 8");
    assert(result1.getRows() == expected1.getRows());
    assert(result1.getCols() == expected1.getCols());
    assert(result1.getData() == expected1.getData());
    // Тест 2: Умножение матрицы на 0
    Matrix matrix2("2 2 1 2 3 4");
    Matrix result2 = matrix2 * 0;
    Matrix expected2("2 2 0 0 0 0");
    assert(result2.getRows() == expected2.getRows());
    assert(result2.getCols() == expected2.getCols());
    assert(result2.getData() == expected2.getData());
}

void testMatrixMultiplication() {
    // Тест 1: Умножение матриц
    Matrix matrix1("2 2 1 2 3 4");
    Matrix matrix2("2 2 5 6 7 8");
    Matrix result1 = matrix1 * matrix2;
    Matrix expected1("2 2 19 22 43 50");
    assert(result1.getRows() == expected1.getRows());
    assert(result1.getCols() == expected1.getCols());
    assert(result1.getData() == expected1.getData());

    // Тест 2: Ошибка неправильных размеров матриц для умножения
    Matrix matrix3("2 3 1 2 3 4 5 6");
    Matrix matrix4("2 3 1 2 3 4 5 6");
    try {
        Matrix result2 = matrix3 * matrix4;
        assert(false); // Можно использовать, если ожидается исключение
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Нельзя умножать матрицы так как количество столбцов матрицы А не равно количеству строк матрицы В";
        assert(error.what() == expectedError);
    }
}

void testDeterminant() {
    // Тест 1: Вычисление определителя квадратной матрицы
    Matrix matrix1("3 3 1 2 3 4 5 6 7 8 9");
    double determinant1 = matrix1.findDeterminant();
    double expectedDeterminant1 = 0;
    assert(determinant1 == expectedDeterminant1);

    // Тест 2: Ошибка вычисления определителя для не квадратной матрицы
    Matrix matrix2("2 3 1 2 3 4 5 6");
    try {
        double determinant2 = matrix2.findDeterminant();
        assert(false); // Можно использовать, если ожидается исключение
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Определитель мжно считать только для квадратной матрицы";
        assert(error.what() == expectedError);
    }
}

void testInverseMatrix() {
    //Тест 1: матрица 3 на 3
    std::vector<std::vector<double>> matrixData = {
        {1, 2, 3},
        {4, 7, 6},
        {7, 8, 9}
    };
    Matrix matrix(matrixData);
    Matrix inverseMatrix = matrix.inverseMatrix();
    // Проверяем размеры обратной матрицы
    assert(inverseMatrix.getRows() == 3);
    assert(inverseMatrix.getCols() == 3);
    // Проверяем значения элементов обратной матрицы
    double epsilon = 1e-6;
    assert(std::abs(inverseMatrix.getData()[0][0] + 0.625) < epsilon);
    assert(std::abs(inverseMatrix.getData()[0][1] + 0.25) < epsilon);
    assert(std::abs(inverseMatrix.getData()[0][2] - 0.375) < epsilon);
    assert(std::abs(inverseMatrix.getData()[1][0] + 0.25) < epsilon);
    assert(std::abs(inverseMatrix.getData()[1][1] - 0.5) < epsilon);
    assert(std::abs(inverseMatrix.getData()[1][2] + 0.25) < epsilon);
    assert(std::abs(inverseMatrix.getData()[2][0] - 0.708333) < epsilon);
    assert(std::abs(inverseMatrix.getData()[2][1] + 0.25) < epsilon);
    assert(std::abs(inverseMatrix.getData()[2][2] - 0.04166667) < epsilon);
    
    //Тест 2: вырожденная матрица 3 на 3
    std::vector<std::vector<double>> matrixData2 = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };
    Matrix matrix2(matrixData2);
    try {
        Matrix inverseMatrix2 = matrix2.inverseMatrix();
        assert(false); // Можно использовать, если ожидается исключение
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Матрица должна быть не вырожденной для вычисления обратной матрицы";
        assert(error.what() == expectedError);
    }

    //Тест 3: неквадратная матрица 3 на 3
    std::vector<std::vector<double>> matrixData3 = {
        {1, 2, 3},
        {4, 7, 6}
    };
    Matrix matrix3(matrixData3);
    try {
        Matrix inverseMatrix3 = matrix3.inverseMatrix();
        assert(false); // Можно использовать, если ожидается исключение
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Матрица должна быть квадратной для вычисления обратной матрицы";
        assert(error.what() == expectedError);
    }
}

void testMatrixTranspose() {
    //Тест 1: матрица 3 на 3
    std::vector<std::vector<double>> matrixData = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };
    std::vector<std::vector<double>> transposedMatrixData = {
        {1, 4, 7},
        {2, 5, 8},
        {3, 6, 9}
    };
    Matrix matrix(matrixData);
    Matrix transposedMatrix = matrix.transpose();
    // Проверяем размеры транспонированной матрицы
    assert(transposedMatrix.getRows() == 3);
    assert(transposedMatrix.getCols() == 3);
    // Проверяем значения элементов транспонированной матрицы
    assert(transposedMatrix.getData() == transposedMatrixData);

    //Тест 2: матрица 2 на 3
    std::vector<std::vector<double>> matrixData2 = {
        {1, 2, 3},
        {4, 5, 6},
    };
    std::vector<std::vector<double>> transposedMatrixData2 = {
        {1, 4},
        {2, 5},
        {3, 6}
    };
    Matrix matrix2(matrixData2);
    Matrix transposedMatrix2 = matrix2.transpose();
    // Проверяем размеры транспонированной матрицы
    assert(transposedMatrix2.getRows() == 3);
    assert(transposedMatrix2.getCols() == 2);
    // Проверяем значения элементов транспонированной матрицы
    assert(transposedMatrix2.getData() == transposedMatrixData2);


}
int main() {
    testConstructor();
    std::cout<<"Constructor tests passed\n";
    testAddition();
    std::cout<<"Addition tests passed\n";
    testSubtraction();
    std::cout<<"Subtraction tests passed\n";
    testScalarMultiplication();
    std::cout<<"ScalarMultiplication tests passed\n";
    testMatrixMultiplication();
    std::cout<<"MatrixMultiplication tests passed\n";
    testDeterminant();
    std::cout<<"Determinant tests passed\n";
    testInverseMatrix();
    std::cout<<"InverseMatrix tests passed\n";
    testMatrixTranspose();
    std::cout<<"MatrixTranspose tests passed\n";
    std::cout << "All tests passed!" << std::endl;

    return 0;
}