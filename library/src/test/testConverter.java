package test;

import library.Converter;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class testConverter {

    @Test
    public void testConvertBase_2to10() {
        assertEquals("5.7500000000", Converter.convertBase("101.11", 2, 10));
    }

    @Test
    public void testConvertBase_16to10() {
        assertEquals("255.5000000000", Converter.convertBase("FF.8", 16, 10));
    }

    @Test
    public void testConvertBase_10to2() {
        assertEquals("1111011.0111010010", Converter.convertBase("123.456", 10, 2));
    }

    @Test
    public void testConvertBase_10to16_zero() {
        assertEquals("0", Converter.convertBase("0", 10, 16));
    }

    @Test
    public void testConvertBase_10to2_fraction() {
        assertEquals("0.1000000000", Converter.convertBase("0.5", 10, 2));
    }

    @Test
    public void testConvertBase_2to8() {
        assertEquals("2", Converter.convertBase("10", 2, 8));
    }

    @Test
    public void testConvertBase_2to16_fraction() {
        assertEquals("A.A000000000", Converter.convertBase("1010.1010", 2, 16));
    }

    @Test
    public void testConvertBase_16to8() {
        assertEquals("5274", Converter.convertBase("ABC", 16, 8));
    }

    @Test
    public void testConvertBase_8to10() {
        assertEquals("4095.9997558593", Converter.convertBase("7777.7777", 8, 10));
    }

    @Test
    public void testConvertBase_16to2_fraction() {
        assertEquals("1111101000.1111000000", Converter.convertBase("3E8.F", 16, 2));
    }

    @Test
    public void testConvertBase_invalidInput_10to2() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Converter.convertBase("12A", 10, 2);
        });
        assertEquals("Invalid input number; does not match the source base!", exception.getMessage());
    }

    @Test
    public void testConvertBase_invalidInput_16to3() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Converter.convertBase("GHI", 16, 3);
        });
        assertEquals("Invalid input number; does not match the source base!", exception.getMessage());
    }

    @Test
    public void testConvertBase_invalidInput_2to7() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Converter.convertBase("12345", 2, 7);
        });
        assertEquals("Invalid input number; does not match the source base!", exception.getMessage());
    }

    @Test
    public void testValidateNumber_valid_10() {
        assertTrue(Converter.validateNumber("12345", 10));
    }

    @Test
    public void testValidateNumber_valid_2() {
        assertTrue(Converter.validateNumber("10101", 2));
    }

    @Test
    public void testValidateNumber_valid_16() {
        assertTrue(Converter.validateNumber("FF", 16));
    }

    @Test
    public void testValidateNumber_valid_16_letters() {
        assertTrue(Converter.validateNumber("ABC", 16));
    }

    @Test
    public void testValidateNumber_valid_8() {
        assertTrue(Converter.validateNumber("123", 8));
    }

    @Test
    public void testValidateNumber_invalid_10() {
        assertFalse(Converter.validateNumber("12A", 10));
    }

    @Test
    public void testValidateNumber_invalid_16() {
        assertFalse(Converter.validateNumber("GHI", 16));
    }

    @Test
    public void testValidateNumber_invalid_2() {
        assertFalse(Converter.validateNumber("12345", 2));
    }
}
