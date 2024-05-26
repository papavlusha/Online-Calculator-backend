package by.spaces.calculator.calculations;

import by.spaces.calculator.calculations.PrimeNumbersCount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutionException;

public class PrimeNumbersCountTest {
    private PrimeNumbersCount primeNumbersCount;

    @BeforeEach
    void setUp() {
        // Этот метод выполняется перед каждым тестом
    }

    @Test
    void testConstructorWithFourParams() {
        primeNumbersCount = new PrimeNumbersCount(2, 100, 2, 10);
        assertNotNull(primeNumbersCount);
    }

    @Test
    void testConstructorWithThreeParams() {
        primeNumbersCount = new PrimeNumbersCount(2, 100, 2);
        assertNotNull(primeNumbersCount);
    }

    @Test
    void testConstructorWithTwoParams() {
        primeNumbersCount = new PrimeNumbersCount(2, 100);
        assertNotNull(primeNumbersCount);
    }

    @Test
    void testConstructorWithOneParam() {
        primeNumbersCount = new PrimeNumbersCount(100);
        assertNotNull(primeNumbersCount);
    }

    @Test
    void testCalculatePrimeCountValidRange() throws ExecutionException, InterruptedException {
        primeNumbersCount = new PrimeNumbersCount(2, 100, 2, 10);
        String[] result = primeNumbersCount.calculatePrimeCount();
        assertNotNull(result);
        assertEquals(2, result.length);
        assertTrue(Integer.parseInt(result[0]) > 0);
        assertEquals(Integer.parseInt(result[0]), 25);
        assertTrue(Double.compare(Double.parseDouble(result[1]), 0.0) >= 0);
    }

    @Test
    void testCalculatePrimeCountWithLargeRange() throws ExecutionException, InterruptedException {
        primeNumbersCount = new PrimeNumbersCount(2, 100000, 4, 10000);
        String[] result = primeNumbersCount.calculatePrimeCount();
        assertNotNull(result);
        assertEquals(2, result.length);
        assertTrue(Integer.parseInt(result[0]) > 0);
        assertEquals(Integer.parseInt(result[0]), 9592);
        assertTrue(Double.parseDouble(result[1]) > 0.0);
    }

    @Test
    void testCalculatePrimeCountInvalidThreadsNum() {
        primeNumbersCount = new PrimeNumbersCount(2, 100, 31, 10);
        assertThrows(IllegalArgumentException.class, () -> {
            primeNumbersCount.calculatePrimeCount();
        });
    }

    @Test
    void testCalculatePrimeCountInvalidCycle() {
        primeNumbersCount = new PrimeNumbersCount(2, 100, 2, 0);
        assertThrows(IllegalArgumentException.class, () -> {
            primeNumbersCount.calculatePrimeCount();
        });
    }
}