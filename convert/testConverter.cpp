#include <iostream>
#include <cassert>
#include "converter.h"


void testConvertBase() {
    // Тестовые случаи
    assert(convertBase("101.11", 2, 10) == "5.7500000000");
    assert(convertBase("FF.8", 16, 10) == "255.5000000000");
    assert(convertBase("123.456", 10, 2) == "1111011.0111010010");
    assert(convertBase("0", 10, 16) == "0");
    assert(convertBase("0.5", 10, 2) == "0.1000000000");
    assert(convertBase("10", 2, 8) == "2");
    assert(convertBase("1010.1010", 2, 16) == "A.A000000000");
    assert(convertBase("ABC", 16, 8) == "5274");
    assert(convertBase("7777.7777", 8, 10) == "4095.9997558593");
    assert(convertBase("3E8.F", 16, 2) == "1111101000.1111000000");
    try {
        std::string ans = convertBase("12A", 10, 2);
        assert(false);
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Неправильный ввод числа, не совпадает с системой счисления!";
        assert(error.what() == expectedError);
    }
    try {
        std::string ans = convertBase("GHI", 16, 3);
        assert(false); 
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Неправильный ввод числа, не совпадает с системой счисления!";
        assert(error.what() == expectedError);
    }
    try {
        std::string ans = convertBase("12345", 2, 7);
        assert(false); 
    } catch (const std::runtime_error& error) {
        std::string expectedError = "Неправильный ввод числа, не совпадает с системой счисления!";
        assert(error.what() == expectedError);
    }
}
void testValidateNumber() {
    // Тестовые случаи
    assert(validateNumber("12345", 10) == true);
    assert(validateNumber("10101", 2) == true);
    assert(validateNumber("FF", 16) == true);
    assert(validateNumber("ABC", 16) == true);
    assert(validateNumber("123", 8) == true); 
    assert(validateNumber("10101", 8) == true); 
    assert(validateNumber("12A", 10) == false); // Цифра A недопустима в десятичной системе счисления
    assert(validateNumber("GHI", 16) == false); // Цифра G недопустима в шестнадцатеричной системе счисления
    assert(validateNumber("12345", 2) == false); // Цифра 2 недопустима в двоичной системе счисления

}
int main() {
    testValidateNumber();
    std::cout << "Тесты для validateNumber пройдены успешно!" << std::endl;
    testConvertBase();
    std::cout << "Тесты для convertBase пройдены успешно!" << std::endl;

    std::cout << "All tests passed!" << std::endl;
    return 0;
}