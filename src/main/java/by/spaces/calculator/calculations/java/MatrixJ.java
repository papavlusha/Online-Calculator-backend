package by.spaces.calculator.calculations.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatrixJ {
    public int rows;
    public int cols;
    public List<List<Double>> data;

    public MatrixJ() {
        this.rows = 0;
        this.cols = 0;
        this.data = new ArrayList<>();
    }

    public MatrixJ(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<Double> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(0.0);
            }
            this.data.add(row);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public static double[][] convertListListToArray(List<List<Double>> listList) {
        int rows = listList.size();
        int cols = listList.get(0).size();
        double[][] array = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            List<Double> row = listList.get(i);
            for (int j = 0; j < cols; j++) {
                array[i][j] = row.get(j);
            }
        }
        return array;
    }

    public double[][] getData() {
        return convertListListToArray(data);
    }

    public MatrixJ(String inputString) {
        Scanner scanner = new Scanner(inputString);
        if (scanner.hasNextInt()) {
            this.rows = scanner.nextInt();
        } else {
            throw new RuntimeException("Неправильный формат размерности матрицы");
        }
        if (scanner.hasNextInt()) {
            this.cols = scanner.nextInt();
        } else {
            throw new RuntimeException("Неправильный формат размерности матрицы");
        }

        if (rows <= 0 || cols <= 0) {
            throw new RuntimeException("Неправильный формат размерности матрицы");
        }

        this.data = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<Double> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                if (scanner.hasNext()) {
                    String elementStr = scanner.next();
                    try {
                        double element = Double.parseDouble(elementStr);
                        row.add(element);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Неправильный формат элемента матрицы");
                    }
                } else {
                    throw new RuntimeException("Количество элементов матрицы не соответствует размерности");
                }
            }
            this.data.add(row);
        }
    }

    public MatrixJ add(MatrixJ other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new RuntimeException("Нельзя складывать матрицы разных размеров");
        }

        MatrixJ result = new MatrixJ(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.data.get(i).set(j, this.data.get(i).get(j) + other.data.get(i).get(j));
            }
        }
        return result;
    }

    public MatrixJ subtract(MatrixJ other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new RuntimeException("Нельзя вычитать матрицы разных размеров");
        }

        MatrixJ result = new MatrixJ(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.data.get(i).set(j, this.data.get(i).get(j) - other.data.get(i).get(j));
            }
        }
        return result;
    }

    public MatrixJ multiply(double scalar) {
        MatrixJ result = new MatrixJ(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.data.get(i).set(j, this.data.get(i).get(j) * scalar);
            }
        }
        return result;
    }

    public MatrixJ multiply(MatrixJ other) {
        if (this.cols != other.rows) {
            throw new RuntimeException("Нельзя умножать матрицы так как количество столбцов матрицы А не равно количеству строк матрицы В");
        }


        MatrixJ result = new MatrixJ(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                for (int k = 0; k < this.cols; k++) {
                    result.data.get(i).set(j, result.data.get(i).get(j) + this.data.get(i).get(k) * other.data.get(k).get(j));
                }
            }
        }
        return result;
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(data.get(i).get(j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private double calculateDeterminant(List<List<Double>> matrix) {
        int size = matrix.size();
        if (size == 1) {
            return matrix.get(0).get(0);
        }

        double determinant = 0;
        int sign = 1;

        for (int i = 0; i < size; i++) {
            List<List<Double>> subMatrix = new ArrayList<>(size - 1);
            for (int j = 1; j < size; j++) {
                List<Double> row = new ArrayList<>(size - 1);
                for (int l = 0; l < size; l++) {
                    if (l != i) {
                        row.add(matrix.get(j).get(l));
                    }
                }
                subMatrix.add(row);
            }

            determinant += sign * matrix.get(0).get(i) * calculateDeterminant(subMatrix);
            sign = -sign;
        }

        return determinant;
    }

    public double findDeterminant() {
        if (this.rows != this.cols) {
            throw new RuntimeException("Определитель можно считать только для квадратной матрицы");
        }

        return calculateDeterminant(this.data);
    }

    public MatrixJ inverseMatrix() {
        return inverse(this.rows, this.cols, this.data);
    }

    private MatrixJ inverse(int rows, int cols, List<List<Double>> matrix) {
        if (rows != cols) {
            throw new RuntimeException("Матрица должна быть квадратной для вычисления обратной матрицы");
        }

        MatrixJ mtr = new MatrixJ(matrix);
        double det = mtr.findDeterminant();
        if (det == 0) {
            throw new RuntimeException("Матрица должна быть не вырожденной для вычисления обратной матрицы");
        }

        int n = rows;
        List<List<Double>> augmentedMatrix = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>(2 * n);
            for (int j = 0; j < n; j++) {
                row.add(matrix.get(i).get(j));
            }
            for (int j = 0; j < n; j++) {
                row.add((i == j) ? 1.0 : 0.0);
            }
            augmentedMatrix.add(row);
        }

        for (int i = 0; i < n; i++) {
            int pivotRow = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(augmentedMatrix.get(j).get(i)) > Math.abs(augmentedMatrix.get(pivotRow).get(i))) {
                    pivotRow = j;
                }
            }

            if (pivotRow != i) {
                List<Double> temp = augmentedMatrix.get(i);
                augmentedMatrix.set(i, augmentedMatrix.get(pivotRow));
                augmentedMatrix.set(pivotRow, temp);
            }

            double pivot = augmentedMatrix.get(i).get(i);
            if (pivot == 0.0) {
                throw new RuntimeException("Матрица вырожденная, обратная матрица не существует");
            }

            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix.get(i).set(j, augmentedMatrix.get(i).get(j) / pivot);
            }

            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmentedMatrix.get(k).get(i);
                    for (int j = 0; j < 2 * n; j++) {
                        augmentedMatrix.get(k).set(j, augmentedMatrix.get(k).get(j) - factor * augmentedMatrix.get(i).get(j));
                    }
                }
            }
        }


        List<List<Double>> inverseMatrix = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                row.add(augmentedMatrix.get(i).get(j + n));
            }
            inverseMatrix.add(row);
        }
        return new MatrixJ(inverseMatrix);
    }

    public MatrixJ transpose() {
        List<List<Double>> transposed = new ArrayList<>(cols);
        for (int i = 0; i < cols; i++) {
            List<Double> row = new ArrayList<>(rows);
            for (int j = 0; j < rows; j++) {
                row.add(data.get(j).get(i));
            }
            transposed.add(row);
        }
        return new MatrixJ(transposed);
    }

    public MatrixJ(List<List<Double>> data) {
        this.rows = data.size();
        this.cols = data.get(0).size();
        this.data = data;
    }

    public MatrixJ(double[][] data) {
        List<List<Double>> newData = convertArrayToListList(data);
        this.rows = newData.size();
        this.cols = newData.get(0).size();
        this.data = newData;
    }

    public static List<List<Double>> convertArrayToListList(double[][] array) {
        List<List<Double>> listList = new ArrayList<>();
        for (double[] row : array) {
            List<Double> list = new ArrayList<>();
            for (double value : row) {
                list.add(value);
            }
            listList.add(list);
        }
        return listList;
    }
}
