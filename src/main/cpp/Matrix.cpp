#include "by_spaces_calculator_calculations_Matrix.h"
#include "matrix/matrix.h"
#include "matrix/matrix.cpp"
#include <string>

// Получить указатель на C++ объект из поля Java объекта
Matrix* getHandle(JNIEnv* env, jobject obj) {
    jclass cls = env->GetObjectClass(obj);
    jfieldID handleField = env->GetFieldID(cls, "nativeHandle", "J");
    jlong handle = env->GetLongField(obj, handleField);
    return reinterpret_cast<Matrix*>(handle);
}

// Сохранить указатель на C++ объект в поле Java объекта
void setHandle(JNIEnv* env, jobject obj, Matrix* mat) {
    jclass cls = env->GetObjectClass(obj);
    jfieldID handleField = env->GetFieldID(cls, "nativeHandle", "J");
    jlong handle = reinterpret_cast<jlong>(mat);
    env->SetLongField(obj, handleField, handle);
}

std::vector<std::vector<double>> convertJobjectArrayToVector(JNIEnv* env, jobjectArray array) {
    jsize outerSize = env->GetArrayLength(array);
    std::vector<std::vector<double>> result(outerSize);

    for (jsize i = 0; i < outerSize; i++) {
        jdoubleArray innerArray = (jdoubleArray)env->GetObjectArrayElement(array, i);
        jsize innerSize = env->GetArrayLength(innerArray);
        jdouble* elements = env->GetDoubleArrayElements(innerArray, 0);

        result[i].resize(innerSize);
        for (jsize j = 0; j < innerSize; j++) {
            result[i][j] = elements[j];
        }

        env->ReleaseDoubleArrayElements(innerArray, elements, 0);
        env->DeleteLocalRef(innerArray);
    }

    return result;
}


JNIEXPORT void JNICALL Java_by_spaces_calculator_calculations_Matrix_init__
  (JNIEnv* env, jobject obj) {
    Matrix* mat = new Matrix();
    setHandle(env, obj, mat);
}

JNIEXPORT void JNICALL Java_by_spaces_calculator_calculations_Matrix_init__II
  (JNIEnv* env, jobject obj, jint rows, jint cols) {
    Matrix* mat = new Matrix(rows, cols);
    setHandle(env, obj, mat);
}

JNIEXPORT void JNICALL Java_by_spaces_calculator_calculations_Matrix_init__Ljava_lang_String_2
  (JNIEnv* env, jobject obj, jstring inputString) {
    const char* inputCString = env->GetStringUTFChars(inputString, 0);
    std::string inputStdStr(inputCString);
    Matrix* mat = new Matrix(inputStdStr);
    env->ReleaseStringUTFChars(inputString, inputCString);
    setHandle(env, obj, mat);
}

JNIEXPORT void JNICALL Java_by_spaces_calculator_calculations_Matrix_init___3_3D
  (JNIEnv* env, jobject obj, jobjectArray matrixData) {
    std::vector<std::vector<double>> convertedMatrixData = convertJobjectArrayToVector(env, matrixData);
    Matrix* mat = new Matrix(convertedMatrixData);
    setHandle(env, obj, mat);
}

JNIEXPORT jint JNICALL Java_by_spaces_calculator_calculations_Matrix_getRows
  (JNIEnv* env, jobject obj) {
    Matrix* mat = getHandle(env, obj);
    return mat->getRows();
}

JNIEXPORT jint JNICALL Java_by_spaces_calculator_calculations_Matrix_getCols
  (JNIEnv* env, jobject obj) {
    Matrix* mat = getHandle(env, obj);
    return mat->getCols();
}

JNIEXPORT jobjectArray JNICALL Java_by_spaces_calculator_calculations_Matrix_getData
  (JNIEnv* env, jobject obj) {
    Matrix* mat = getHandle(env, obj);
    std::vector<std::vector<double>> data = mat->getData();

    jclass doubleArrayClass = env->FindClass("[D");
    jobjectArray result = env->NewObjectArray(data.size(), doubleArrayClass, NULL);

    for (size_t i = 0; i < data.size(); i++) {
        jdoubleArray doubleArray = env->NewDoubleArray(data[i].size());
        env->SetDoubleArrayRegion(doubleArray, 0, data[i].size(), &data[i][0]);
        env->SetObjectArrayElement(result, i, doubleArray);
        env->DeleteLocalRef(doubleArray);
    }

    return result;
}

// Создание нового Java объекта Matrix
jobject createJavaMatrix(JNIEnv* env, Matrix* mat) {
    jclass cls = env->FindClass("by/spaces/calculator/calculations/Matrix");
    jmethodID constructor = env->GetMethodID(cls, "<init>", "()V");
    jobject obj = env->NewObject(cls, constructor);
    setHandle(env, obj, mat);
    return obj;
}

JNIEXPORT jobject JNICALL Java_by_spaces_calculator_calculations_Matrix_add
  (JNIEnv* env, jobject obj, jobject otherObj) {
    Matrix* mat = getHandle(env, obj);
    Matrix* otherMat = getHandle(env, otherObj);
    Matrix* resultMat = new Matrix(*mat + *otherMat);
    return createJavaMatrix(env, resultMat);
}

JNIEXPORT jobject JNICALL Java_by_spaces_calculator_calculations_Matrix_subtract
  (JNIEnv* env, jobject obj, jobject otherObj) {
    Matrix* mat = getHandle(env, obj);
    Matrix* otherMat = getHandle(env, otherObj);
    Matrix* resultMat = new Matrix(*mat - *otherMat);
    return createJavaMatrix(env, resultMat);
}

JNIEXPORT jobject JNICALL Java_by_spaces_calculator_calculations_Matrix_multiply__D
  (JNIEnv* env, jobject obj, jdouble scalar) {
    Matrix* mat = getHandle(env, obj);
    Matrix* resultMat = new Matrix(*mat * scalar);
    return createJavaMatrix(env, resultMat);
}

JNIEXPORT jobject JNICALL Java_by_spaces_calculator_calculations_Matrix_multiply__Lby_spaces_calculator_calculations_Matrix_2
  (JNIEnv* env, jobject obj, jobject otherObj) {
    Matrix* mat = getHandle(env, obj);
    Matrix* otherMat = getHandle(env, otherObj);
    Matrix* resultMat = new Matrix(*mat * *otherMat);
    return createJavaMatrix(env, resultMat);
}


JNIEXPORT jdouble JNICALL Java_by_spaces_calculator_calculations_Matrix_findDeterminant
  (JNIEnv* env, jobject obj) {
    Matrix* mat = getHandle(env, obj);
    return mat->findDeterminant();
}

JNIEXPORT void JNICALL Java_by_spaces_calculator_calculations_Matrix_print
  (JNIEnv* env, jobject obj) {
    Matrix* mat = getHandle(env, obj);
    mat->print();
}

JNIEXPORT jobject JNICALL Java_by_spaces_calculator_calculations_Matrix_inverseMatrix
  (JNIEnv* env, jobject obj) {
    Matrix* mat = getHandle(env, obj);
    Matrix* resultMat = new Matrix(mat->inverseMatrix());
    return createJavaMatrix(env, resultMat);
}

JNIEXPORT jobject JNICALL Java_by_spaces_calculator_calculations_Matrix_transpose
  (JNIEnv* env, jobject obj) {
    Matrix* mat = getHandle(env, obj);
    Matrix* resultMat = new Matrix(mat->transpose());
    return createJavaMatrix(env, resultMat);
}

