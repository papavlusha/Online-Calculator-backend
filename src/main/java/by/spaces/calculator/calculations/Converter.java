package by.spaces.calculator.calculations;

public interface Converter {
    String convertBase(String number, int sourceBase, int targetBase);
    boolean validateNumber(String number, int base);
}
