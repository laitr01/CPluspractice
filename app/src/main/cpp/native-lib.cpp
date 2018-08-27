//
// Created by ltrach on 8/20/2018.
//

#include <jni.h>
#include <string>

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
                JNIEnv* env,
        jobject
){
    printf("Hello from C++");
}

