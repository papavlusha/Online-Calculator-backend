package by.spaces.calculator.calculations.cpp;

import by.spaces.calculator.calculations.Converter;

import java.io.IOException;

import static by.spaces.calculator.calculations.ExtractLibClass.extractLibrary;


public class ConverterC implements Converter {
    static {
        try {
            System.load(extractLibrary("Converter"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public native String convertBase(String number, int sourceBase, int targetBase);
    public native boolean validateNumber(String number, int base);
}
