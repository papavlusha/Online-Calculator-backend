#ifndef CONVERTER_H
#define CONVERTER_H

#include <string>
#include <iostream>
#include <cmath>
std::string convertBase(const std::string& number, int sourceBase, int targetBase);
bool validateNumber(const std::string& number, int base);
#endif // CONVERTER_H