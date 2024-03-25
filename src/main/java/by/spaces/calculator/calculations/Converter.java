package by.spaces.calculator.calculations;

public class Converter {
    static {
        System.loadLibrary("converter");
    }

    public native String convertBase(String number, int sourceBase, int targetBase);
    public native boolean validateNumber(String number, int base);
}
