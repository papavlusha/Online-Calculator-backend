// #include <iostream>
// #include <string>
// #include <cmath>
#include "converter.h"

bool validateNumber(const std::string& number, int base) {

    // Проверка наличия недопустимых символов
    for (char digit : number) {
        if ((digit != '.') && !isdigit(digit) && (tolower(digit) < 'a' || tolower(digit) > 'z')) {
            return false;
        }
    }

    // Проверка соответствия цифр системе счисления
    for (char digit : number) {
        if (isdigit(digit) && (digit - '0' >= base)) {
            return false;
        }
        if (isalpha(digit) && (tolower(digit) - 'a' + 10 >= base)) {
            return false;
        }
    }

    return true;
}
// Функция для конвертирования числа из одной системы счисления в другую
std::string convertBase(const std::string& number, int sourceBase, int targetBase) {
    if(!validateNumber(number, sourceBase)){
        throw std::runtime_error("Неправильный ввод числа, не совпадает с системой счисления!");
    }
    // Проверка наличия десятичной точки
    if(number == "0"){
        return "0";
    }
    size_t dotPos = number.find('.');
    bool hasFractionalPart = (dotPos != std::string::npos);

    // Разделение целой и десятичной частей числа
    std::string integerPart = number.substr(0, dotPos);
    std::string fractionalPart = (hasFractionalPart) ? number.substr(dotPos + 1) : "";

    // Конвертация целой части
    int integer = 0;
    for (char digit : integerPart) {
        int value = (isdigit(digit)) ? (digit - '0') : (tolower(digit) - 'a' + 10);
        integer = integer * sourceBase + value;
    }

    // Конвертация десятичной части
    double fractional = 0.0;
    double baseFraction = 1.0 / sourceBase;
    for (char digit : fractionalPart) {
        int value = (isdigit(digit)) ? (digit - '0') : (tolower(digit) - 'a' + 10);
        fractional += value * baseFraction;
        baseFraction /= sourceBase;
    }

    // Конвертация в целевую систему счисления
    std::string result = "";
    if(integer == 0){
        result = "0";
    }
    while (integer > 0) {
        int digit = integer % targetBase;
        char digitChar = (digit < 10) ? ('0' + digit) : ('A' + digit - 10);
        result = digitChar + result;
        integer /= targetBase;
    }

    // Добавление десятичной точки и конвертация десятичной части
    if (hasFractionalPart) {
        result += ".";
        const int maxFractionalDigits = 10;
        for (int i = 0; i < maxFractionalDigits; ++i) {
            fractional *= targetBase;
            int digit = static_cast<int>(floor(fractional));
            char digitChar = (digit < 10) ? ('0' + digit) : ('A' + digit - 10);
            result += digitChar;
            fractional -= digit;
        }
    }

    return result;
}

// int main() {
//     std::string number;
//     int sourceBase, targetBase;

//     std::cout << "Введите число: ";
//     std::cin >> number;

//     std::cout << "Введите исходную систему счисления: ";
//     std::cin >> sourceBase;

//     std::cout << "Введите целевую систему счисления: ";
//     std::cin >> targetBase;

//     std::string convertedNumber = convertBase(number, sourceBase, targetBase);
//     std::cout << "Результат конвертации: " << convertedNumber << std::endl;

//     return 0;
// }