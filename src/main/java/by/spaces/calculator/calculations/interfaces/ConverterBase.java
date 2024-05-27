package by.spaces.calculator.calculations.interfaces;

public interface ConverterBase {
    String convertBase(String number, int sourceBase, int targetBase);
    boolean validateNumber(String number, int base);

}
