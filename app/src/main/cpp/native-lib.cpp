#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>

extern "C" JNIEXPORT jstring JNICALL
Java_com_poly_opencv_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {

    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}