package test;
import library.Matrix;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class testMatrix {

    @Test
    void testConstructor() {
        // Тест 1: Создание пустой матрицы
        Matrix matrix1 = new Matrix();
        assertEquals(0, matrix1.getRows());
        assertEquals(0, matrix1.getCols());

        // Тест 2: Создание матрицы размером 2x3
        Matrix matrix2 = new Matrix(2, 3);
        assertEquals(2, matrix2.getRows());
        assertEquals(3, matrix2.getCols());
        assertEquals(Arrays.asList(
            Arrays.asList(0.0, 0.0, 0.0),
            Arrays.asList(0.0, 0.0, 0.0)
        ), matrix2.getData());

        // Тест 3: Создание матрицы из строки
        String inputString = "2 3 1 2 3 4 5 6";
        Matrix matrix3 = new Matrix(inputString);
        assertEquals(2, matrix3.getRows());
        assertEquals(3, matrix3.getCols());
        assertEquals(Arrays.asList(
            Arrays.asList(1.0, 2.0, 3.0),
            Arrays.asList(4.0, 5.0, 6.0)
        ), matrix3.getData());

        // Тест 4: Ошибка неверного количества элементов
        final String invalidInputString1 = "2 3 1 2 3 4";
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new Matrix(invalidInputString1);
        });
        assertEquals("Количество элементов матрицы не соответствует размерности", exception.getMessage());

        // Тест 5: Ошибка дробных размеров матрицы
        final String invalidInputString2 = "2.1 3.2 1 2 3 4 5 6";
        exception = assertThrows(RuntimeException.class, () -> {
            new Matrix(invalidInputString2);
        });
        assertEquals("Неправильный формат размерности матрицы", exception.getMessage());

        // Тест 6: Ошибка ввода не чисел
        final String invalidInputString3 = "2 3 1 2 а б 5 6";
        exception = assertThrows(RuntimeException.class, () -> {
            new Matrix(invalidInputString3);
        });
        assertEquals("Неправильный формат элемента матрицы", exception.getMessage());

        // Тест 7: Ошибка букв в размерах матрицы
        final String invalidInputString4 = "ф 3 1 2 3 4 5 6";
        exception = assertThrows(RuntimeException.class, () -> {
            new Matrix(invalidInputString4);
        });
        assertEquals("Неправильный формат размерности матрицы", exception.getMessage());

        // Тест 8: Ошибка отрицательных размеров матрицы
        final String invalidInputString5 = "-2 3 1 2 3 4 5 6";
        exception = assertThrows(RuntimeException.class, () -> {
            new Matrix(invalidInputString5);
        });
        assertEquals("Неправильный формат размерности матрицы", exception.getMessage());

        // Тест 9: Создание матрицы размером 2x3 из вектора
        List<List<Double>> testVec9 = Arrays.asList(
            Arrays.asList(1.0, 2.0, 3.0),
            Arrays.asList(4.0, 5.0, 6.0)
        );
        Matrix matrix9 = new Matrix(testVec9);
        assertEquals(2, matrix9.getRows());
        assertEquals(3, matrix9.getCols());
        assertEquals(testVec9, matrix9.getData());
    }

    @Test
    void testAddition() {
        // Тест 1: Сложение матриц одинакового размера
        Matrix matrix1 = new Matrix("2 2 1 2 3 4");
        Matrix matrix2 = new Matrix("2 2 5 6 7 8");
        Matrix result1 = matrix1.add(matrix2);
        Matrix expected1 = new Matrix("2 2 6 8 10 12");
        assertEquals(expected1.getRows(), result1.getRows());
        assertEquals(expected1.getCols(), result1.getCols());
        assertEquals(expected1.getData(), result1.getData());

        // Тест 2: Ошибка неправильных размеров матриц для сложения
        Matrix matrix3 = new Matrix("2 3 1 2 3 4 5 6");
        Matrix matrix4 = new Matrix("3 2 1 2 3 4 5 6");
        Exception exception = assertThrows(RuntimeException.class, () -> {
            matrix3.add(matrix4);
        });
        assertEquals("Нельзя складывать матрицы разных размеров", exception.getMessage());
    }


    @Test
    void testSubtraction() {
        // Тест 1: Вычитание матриц одинакового размера
        Matrix matrix1 = new Matrix("2 2 5 6.1 7 8");
        Matrix matrix2 = new Matrix("2 2 1 2 3 4");
        Matrix result1 = matrix1.subtract(matrix2);
        Matrix expected1 = new Matrix("2 2 4 4.1 4 4");
        assertEquals(expected1.getRows(), result1.getRows());
        assertEquals(expected1.getCols(), result1.getCols());
        assertEquals(expected1.getData(), result1.getData());

        // Тест 2: Ошибка неправильных размеров матриц для вычитания
        Matrix matrix3 = new Matrix("2 3 1 2 3 4 5 6");
        Matrix matrix4 = new Matrix("3 2 1 2 3 4 5 6");
        Exception exception = assertThrows(RuntimeException.class, () -> {
            matrix3.subtract(matrix4);
        });
        assertEquals("Нельзя вычитать матрицы разных размеров", exception.getMessage());
    }

    @Test
    void testScalarMultiplication() {
        // Тест 1: Умножение матрицы на скаляр
        Matrix matrix1 = new Matrix("2 2 1 2 3 4");
        Matrix result1 = matrix1.multiply(2);
        Matrix expected1 = new Matrix("2 2 2 4 6 8");
        assertEquals(expected1.getRows(), result1.getRows());
        assertEquals(expected1.getCols(), result1.getCols());
        assertEquals(expected1.getData(), result1.getData());

        // Тест 2: Умножение матрицы на 0
        Matrix matrix2 = new Matrix("2 2 1 2 3 4");
        Matrix result2 = matrix2.multiply(0);
        Matrix expected2 = new Matrix("2 2 0 0 0 0");
        assertEquals(expected2.getRows(), result2.getRows());
        assertEquals(expected2.getCols(), result2.getCols());
        assertEquals(expected2.getData(), result2.getData());
    }

    @Test
    void testMatrixMultiplication() {
        // Тест 1: Умножение матриц
        Matrix matrix1 = new Matrix("2 2 1 2 3 4");
        Matrix matrix2 = new Matrix("2 2 5 6 7 8");
        Matrix result1 = matrix1.multiply(matrix2);
        Matrix expected1 = new Matrix("2 2 19 22 43 50");
        assertEquals(expected1.getRows(), result1.getRows());
        assertEquals(expected1.getCols(), result1.getCols());
        assertEquals(expected1.getData(), result1.getData());

        // Тест 2: Ошибка неправильных размеров матриц для умножения
        Matrix matrix3 = new Matrix("2 3 1 2 3 4 5 6");
        Matrix matrix4 = new Matrix("2 3 1 2 3 4 5 6");
        Exception exception = assertThrows(RuntimeException.class, () -> {
            matrix3.multiply(matrix4);
        });
        assertEquals("Нельзя умножать матрицы так как количество столбцов матрицы А не равно количеству строк матрицы В", exception.getMessage());
    }

    @Test
    void testDeterminant() {
        // Тест 1: Вычисление определителя квадратной матрицы
        Matrix matrix1 = new Matrix("3 3 1 2 3 4 5 6 7 8 9");
        double determinant1 = matrix1.findDeterminant();
        double expectedDeterminant1 = 0;
        assertEquals(expectedDeterminant1, determinant1);

        // Тест 2: Ошибка вычисления определителя для не квадратной матрицы
        Matrix matrix2 = new Matrix("2 3 1 2 3 4 5 6");
        Exception exception = assertThrows(RuntimeException.class, () -> {
            matrix2.findDeterminant();
        });
        assertEquals("Определитель можно считать только для квадратной матрицы", exception.getMessage());
    }

    @Test
    void testInverseMatrix() {
        // Тест 1: Обратная матрица 3x3
        List<List<Double>> matrixData = Arrays.asList(
            Arrays.asList(1.0, 2.0, 3.0),
            Arrays.asList(4.0, 7.0, 6.0),
            Arrays.asList(7.0, 8.0, 9.0)
        );
        Matrix matrix = new Matrix(matrixData);
        Matrix inverseMatrix = matrix.inverseMatrix();
        assertEquals(3, inverseMatrix.getRows());
        assertEquals(3, inverseMatrix.getCols());


        double epsilon = 1e-6;
        assertEquals(-0.625, inverseMatrix.getData().get(0).get(0), epsilon);
        assertEquals(-0.25, inverseMatrix.getData().get(0).get(1), epsilon);
        assertEquals(0.375, inverseMatrix.getData().get(0).get(2), epsilon);
        assertEquals(-0.25, inverseMatrix.getData().get(1).get(0), epsilon);
        assertEquals(0.5, inverseMatrix.getData().get(1).get(1), epsilon);
        assertEquals(-0.25, inverseMatrix.getData().get(1).get(2), epsilon);
        assertEquals(0.708333, inverseMatrix.getData().get(2).get(0), epsilon);
        assertEquals(-0.25, inverseMatrix.getData().get(2).get(1), epsilon);
        assertEquals(0.04166667, inverseMatrix.getData().get(2).get(2), epsilon);

        // Тест 2: Вырожденная матрица 3x3
        List<List<Double>> matrixData2 = Arrays.asList(
            Arrays.asList(1.0, 2.0, 3.0),
            Arrays.asList(4.0, 5.0, 6.0),
            Arrays.asList(7.0, 8.0, 9.0)
        );
        Matrix matrix2 = new Matrix(matrixData2);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            matrix2.inverseMatrix();
        });
        assertEquals("Матрица должна быть не вырожденной для вычисления обратной матрицы", exception.getMessage());

        // Тест 3: Неквадратная матрица 3x3
        List<List<Double>> matrixData3 = Arrays.asList(
            Arrays.asList(1.0, 2.0, 3.0),
            Arrays.asList(4.0, 7.0, 6.0)
        );
        Matrix matrix3 = new Matrix(matrixData3);
        exception = assertThrows(RuntimeException.class, () -> {
            matrix3.inverseMatrix();
        });
        assertEquals("Матрица должна быть квадратной для вычисления обратной матрицы", exception.getMessage());
    }

    @Test
    void testMatrixTranspose() {
        // Тест 1: Транспонирование матрицы 3x3
        List<List<Double>> matrixData = Arrays.asList(
            Arrays.asList(1.0, 2.0, 3.0),
            Arrays.asList(4.0, 5.0, 6.0),
            Arrays.asList(7.0, 8.0, 9.0)
        );
        List<List<Double>> transposedMatrixData = Arrays.asList(
            Arrays.asList(1.0, 4.0, 7.0),
            Arrays.asList(2.0, 5.0, 8.0),
            Arrays.asList(3.0, 6.0, 9.0)
        );
        Matrix matrix = new Matrix(matrixData);
        Matrix transposedMatrix = matrix.transpose();
        assertEquals(3, transposedMatrix.getRows());
        assertEquals(3, transposedMatrix.getCols());
        assertEquals(transposedMatrixData, transposedMatrix.getData());

        // Тест 2: Транспонирование матрицы 2x3
        List<List<Double>> matrixData2 = Arrays.asList(
            Arrays.asList(1.0, 2.0, 3.0),
            Arrays.asList(4.0, 5.0, 6.0)
        );
        List<List<Double>> transposedMatrixData2 = Arrays.asList(
            Arrays.asList(1.0, 4.0),
            Arrays.asList(2.0, 5.0),
            Arrays.asList(3.0, 6.0)
        );
        Matrix matrix2 = new Matrix(matrixData2);
        Matrix transposedMatrix2 = matrix2.transpose();
        assertEquals(3, transposedMatrix2.getRows());
        assertEquals(2, transposedMatrix2.getCols());
        assertEquals(transposedMatrixData2, transposedMatrix2.getData());
    }
}

