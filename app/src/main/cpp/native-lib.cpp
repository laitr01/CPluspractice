//
// Created by ltrach on 8/20/2018.
//

#include <jni.h>
#include <string>
#include "logutils.h"

using namespace std;
extern "C" JNIEXPORT jstring

JNICALL
Java_com_bestapplication_cpluspractice_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT void
JNICALL
Java_com_bestapplication_cpluspractice_MainActivity_printf(
        JNIEnv *env,
        jobject
) {
    printf("Hello from C++");
}

//practice variable types in c++
extern "C" JNIEXPORT jstring
JNICALL
Java_com_bestapplication_cpluspractice_Practice1_sizeOfType(
        JNIEnv *env,
        jobject
) {
    string result = "";
    unsigned char sizeOfChar = sizeof(char);
    LOGE("sizeOfChar -> %d",sizeOfChar);
    result += "sizeOfChar -> " + to_string(sizeOfChar) + "\n";
    unsigned char sizeOfUnsignedChar = sizeof(unsigned char);
    LOGE("sizeOfUnsignedChar -> %d",sizeOfUnsignedChar);
    result += "sizeOfUnsignedChar -> " + to_string(sizeOfUnsignedChar) + "\n";
    unsigned char sizeOfUnsignedInt = sizeof(unsigned int);
    LOGE("sizeOfUnsignedInt -> %d",sizeOfUnsignedInt);
    result += "sizeOfUnsignedInt -> " + to_string(sizeOfUnsignedInt) + "\n";
    unsigned char sizeOfLongDouble = sizeof(long double);
    LOGE("sizeOfLongDouble -> %d",sizeOfLongDouble);
    result += "sizeOfLongDouble -> " + to_string(sizeOfLongDouble) + "\n";
    unsigned char sizeOfDouble = sizeof(double);
    LOGE("sizeOfDouble -> %d", sizeOfDouble);
    result += "sizeOfDouble -> " + to_string(sizeOfDouble);

    return env->NewStringUTF(result.c_str());
}
/**
 * Using Functions and Classes
 * declare and call standard functions and use standard classes.
 * http://www.lmpt.univ-tours.fr/~volkov/C++.pdf
 * Page 39 - 56
 */

//Mathematical standard functions
double sin(int, int); //sine
double cos (double);// Cosine
double tan (double);// Tangent
double atan (double);// Arc tangent
double cosh (double);// Hyperbolic Cosine
double sqrt (double);// Square Root
double pow (double, double);// Power
double exp (double);// Exponential Function
double log (double);// Natural Logarithm
double log10 (double);// Base-ten Logarithm
double square(double);

extern "C" JNIEXPORT jstring
JNICALL
Java_com_bestapplication_cpluspractice_Practice1_usingFunctionsAndClasses(
        JNIEnv *env,
        jobject
) {
    LOGE("sin of triangle -> %f",sin(3, 4)); //sin(a) = opposite/hypotenuse
    string result =  "sin of triangle -> "+ to_string(sin(3, 4)) +"\n";
    result +=  "the square roots of the 4  -> "+ to_string(square(4)) +"\n";
    result +=  "the square roots of the 12.25  -> "+ to_string(square(12.25)) +"\n";
    result +=  "the square roots of the 0.0121  -> "+ to_string(square(0.0121)) +"\n";
    return env -> NewStringUTF(result.c_str());
}

double sin(int x, int y){
    return (double)y/x;
}
/**
 * Create a program to calculate the square roots of the numbers
 * 4 12.25 0.0121
 * @param number
 * @return
 */
double square(double number){
    return (double) number * number;
}