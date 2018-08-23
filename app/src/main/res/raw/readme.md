JNI tips
-------------------------------------------------------------------------------------------------------------------------------
JNI is the Java Native Interface.
It defines a way for the bytecode that Android compiles from managed code
(written in the Java or Kotlin programming languages) to interact with native code (written in C/C++). <----*

General tips
Try to minimize the footprint of your JNI layer
--------------------------------------------------------------------------------------------------------------------------------
Minimize marshalling of resources across the JNI layer.
Avoid asynchronous communication between code written in a managed programming language and code written in C++ when possible.
Avoid asynchronous communication between code written in a managed programming language and code written in C++ when possible.
Keep your interface code in a low number of easily identified C++ and Java source locations to facilitate future refactors.
Consider using a JNI auto-generation library as appropriate.
--------------------------------------------------------------------------------------------------------------------------------

JavaVM and JNIEnv

"JavaVM" and "JNIEnv"  -> pointers to pointers to function tables
-> The JavaVM provides the "invocation interface" functions, which allow you to create and destroy a JavaVM.
In theory you can have multiple JavaVMs per process, but Android only allows one.
-> The JNIEnv provides most of the JNI functions. Your native functions all receive a JNIEnv as the first argument.
The JNIEnv is used for thread-local storage. For this reason, you cannot share a JNIEnv between threads.
--------------------------------------------------------------------------------------------------------------------------------
Threads

All threads are Linux threads, scheduled by the kernel.

--------------------------------------------------------------------------------------------------------------------------------
jclass, jmethodID, and jfieldID

If you want to access an object's field from native code, you would do the following:

Get the class object reference for the class with FindClass
Get the field ID for the field with GetFieldID
Get the contents of the field with something appropriate, such as GetIntField

---------------------------------------------------------------------------------------------------------------------------------
Local and global references

Native libraries
You can load native code from shared libraries with the standard System.loadLibrary. The preferred way to work with native methods is:

Call System.loadLibrary from a static class initializer. The argument is the "undecorated" library name, so to load "libfubar.so" you would pass in "fubar".
Provide a JNI_OnLoad function: JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved)
In your JNI_OnLoad, register all of your native methods using RegisterNatives.
Build with -fvisibility=hidden so that only your JNI_OnLoad is exported from your library.
This produces faster and smaller code, and avoids potential collisions with other libraries loaded into your app
(but it creates less useful stack traces if you app crashes in native code).
The static initializer should look like this:

    static {
        System.loadLibrary("fubar");
    }
The JNI_OnLoad function should look something like this if written in C++:

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    // Get jclass with env->FindClass.
    // Register methods with env->RegisterNatives.

    return JNI_VERSION_1_6;
}
----------------------------------------------------------------------------------------------------------------------------------------
Add C and C++ code to Your project

Android Studio's default build tool for native libraries is CMake.
Android Studio also supports ndk-build due to the large number of existing projects that use the build toolkit to compile their native code.
Your Java or Kotlin code can then call functions in your native library through the Java Native Interface (JNI)
