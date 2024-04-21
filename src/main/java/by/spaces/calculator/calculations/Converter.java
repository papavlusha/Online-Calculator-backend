package by.spaces.calculator.calculations;

import java.io.*;

import static by.spaces.calculator.calculations.ExtractLibClass.extractLibrary;

public class Converter {
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
