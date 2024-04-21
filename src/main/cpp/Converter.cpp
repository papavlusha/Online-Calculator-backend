#include <jni.h>
#include "by_spaces_calculator_calculations_Converter.h"
#include "convert/converter.h"
#include "convert/converter.cpp"

extern "C" {

JNIEXPORT jstring JNICALL Java_by_spaces_calculator_calculations_Converter_convertBase
  (JNIEnv *env, jobject obj, jstring number, jint sourceBase, jint targetBase) {
    const char *numberStr = env->GetStringUTFChars(number, 0);
    std::string result = convertBase(numberStr, sourceBase, targetBase);
    env->ReleaseStringUTFChars(number, numberStr);
    return env->NewStringUTF(result.c_str());
}

JNIEXPORT jboolean JNICALL Java_by_spaces_calculator_calculations_Converter_validateNumber
  (JNIEnv *env, jobject obj, jstring number, jint base) {
    const char *numberStr = env->GetStringUTFChars(number, 0);
    bool isValid = validateNumber(numberStr, base);
    env->ReleaseStringUTFChars(number, numberStr);
    return isValid;
}

}
