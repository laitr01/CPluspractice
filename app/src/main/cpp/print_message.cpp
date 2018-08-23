//
// Created by ltrach on 8/21/2018.
//

#include <jni.h>
#include <string>

JNICALL
Java_com_bestapplication_cpluspractice_MainActivity_stringFromJNI(
        JNIEnv *env,
jobject /* this */) {
std::string hello = "Hello from C++";
return env->NewStringUTF(hello.c_str());
}