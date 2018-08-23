//
// Created by ltrach on 8/21/2018.
//

#include <jni.h>
#include <string>

void Java_com_bestapplication_imageprocessing_MainActivity_printf(
        JNIEnv* env,
        jobject
){
    printf("Hello from C++");
}