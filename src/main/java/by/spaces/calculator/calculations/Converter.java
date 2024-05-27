package by.spaces.calculator.calculations;

import by.spaces.calculator.calculations.interfaces.ConverterBase;

public class Converter implements ConverterBase {
    public native String convertBase(String number, int sourceBase, int targetBase);
    public native boolean validateNumber(String number, int base);
}
