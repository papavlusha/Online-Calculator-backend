#include "by_spaces_calculator_calculations_Converter.h"
#include "convert/converter.h"
#include <string>

// Вспомогательная функция для преобразования jstring в std::string
std::string jstring2string(JNIEnv *env, jstring jStr) {
    if (!jStr)
        return "";

    const jclass stringClass = env->GetObjectClass(jStr);
    const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes, env->NewStringUTF("UTF-8"));

    size_t length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte* pBytes = env->GetByteArrayElements(stringJbytes, NULL);

    std::string ret = std::string((char *)pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return ret;
}

// Реализация функции validateNumber
JNIEXPORT jboolean JNICALL Java_by_spaces_calculator_calculations_Converter_validateNumber
  (JNIEnv *env, jobject, jstring number, jint base) {
    std::string num = jstring2string(env, number);
    return static_cast<jboolean>(validateNumber(num, base));
}

// Реализация функции convertBase
JNIEXPORT jstring JNICALL Java_by_spaces_calculator_calculations_Converter_convertBase
  (JNIEnv *env, jobject, jstring number, jint sourceBase, jint targetBase) {
    std::string num = jstring2string(env, number);
    std::string converted;
    try {
        converted = convertBase(num, sourceBase, targetBase);
    } catch (const std::runtime_error& e) {
        // Обработка исключения
        jclass exClass = env->FindClass("java/lang/RuntimeException");
        env->ThrowNew(exClass, e.what());
    }
    return env->NewStringUTF(converted.c_str());
}
