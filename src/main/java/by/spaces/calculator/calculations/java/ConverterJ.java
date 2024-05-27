package by.spaces.calculator.calculations.java;

import by.spaces.calculator.calculations.Converter;

public class ConverterJ implements Converter {

    public boolean validateNumber(String number, int base) {
        // Check for invalid characters
        for (char digit : number.toCharArray()) {
            if (digit != '.' && !Character.isDigit(digit) && (Character.toLowerCase(digit) < 'a' || Character.toLowerCase(digit) > 'z')) {
                return false;
            }
        }

        // Check that digits fit within the base
        for (char digit : number.toCharArray()) {
            if (Character.isDigit(digit) && (digit - '0' >= base)) {
                return false;
            }
            if (Character.isAlphabetic(digit) && (Character.toLowerCase(digit) - 'a' + 10 >= base)) {
                return false;
            }
        }

        return true;
    }

    public String convertBase(String number, int sourceBase, int targetBase) {
        if (!validateNumber(number, sourceBase)) {
            throw new IllegalArgumentException("Invalid input number; does not match the source base!");
        }

        if (number.equals("0")) {
            return "0";
        }

        int dotPos = number.indexOf('.');
        boolean hasFractionalPart = (dotPos != -1);

        String integerPart = hasFractionalPart ? number.substring(0, dotPos) : number;
        String fractionalPart = hasFractionalPart ? number.substring(dotPos + 1) : "";

        // Convert integer part
        int integer = 0;
        for (char digit : integerPart.toCharArray()) {
            int value = Character.isDigit(digit) ? (digit - '0') : (Character.toLowerCase(digit) - 'a' + 10);
            integer = integer * sourceBase + value;
        }

        // Convert fractional part
        double fractional = 0.0;
        double baseFraction = 1.0 / sourceBase;
        for (char digit : fractionalPart.toCharArray()) {
            int value = Character.isDigit(digit) ? (digit - '0') : (Character.toLowerCase(digit) - 'a' + 10);
            fractional += value * baseFraction;
            baseFraction /= sourceBase;
        }

        // Convert to target base
        StringBuilder result = new StringBuilder();
        if (integer == 0) {
            result.append("0");
        } else {
            while (integer > 0) {
                int digit = integer % targetBase;
                char digitChar = (digit < 10) ? (char) ('0' + digit) : (char) ('A' + digit - 10);
                result.insert(0, digitChar);
                integer /= targetBase;
            }
        }

        // Add fractional part if present
        if (hasFractionalPart) {
            result.append(".");
            final int maxFractionalDigits = 10;
            for (int i = 0; i < maxFractionalDigits; ++i) {
                fractional *= targetBase;
                int digit = (int) Math.floor(fractional);
                char digitChar = (digit < 10) ? (char) ('0' + digit) : (char) ('A' + digit - 10);
                result.append(digitChar);
                fractional -= digit;
            }
        }

        return result.toString();
    }
}
