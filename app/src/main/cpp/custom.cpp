#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>

#include <android/log.h>
#include <opencv2/imgproc/imgproc_c.h>

#define DEBUG_MODE

#define LOG_TAG "OCR"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))

using namespace std;
using namespace cv;
extern "C" {

JNIEXPORT jstring JNICALL Java_com_poly_opencv_OpencvLib_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {

    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT void JNICALL Java_com_poly_opencv_OpencvLib_adaptiveThreshold(JNIEnv *env, jclass clazz, jlong addr_src_img, jlong addr_dst_img) {
    // Conversion en Mat OpenCV
    Mat &inImg = *(Mat *) addr_src_img;
    Mat &outImg = *(Mat *) addr_dst_img;

    try {
        if (inImg.cols > 0 && inImg.rows > 0) {

#ifdef DEBUG_MODE
            LOGD("IMG Size=%d, %d", inImg.cols, inImg.rows);
            LOGD("OPENCVTIME : Image Source=%d, %d", inImg.cols, inImg.rows);
            LOGD("OPENCVTIME : AVANT IMG TRAITEE");
#endif

            // Normalize
            normalize(inImg, inImg, 0, 255, NORM_MINMAX, -1);

            // Adapatative threshold
            int minusValue = 11;
            int blockSize = 11;
            adaptiveThreshold(inImg, outImg, 250, ADAPTIVE_THRESH_GAUSSIAN_C,
                              THRESH_BINARY, blockSize, minusValue);

            // Sharpen
            Mat bluredROI = Mat(outImg.rows, outImg.cols, outImg.type());
            GaussianBlur(outImg, bluredROI, Size(5, 5), 0);
            addWeighted(outImg, 1.4, bluredROI, -0.4, 0,
                        outImg);
            bluredROI.release();


#ifdef DEBUG_MODE
            LOGD("OPENCVTIME : APRES IMG TRAITEE");
#endif
        }
    } catch (cv::Exception &e) {
        LOGD("CV EXCEPTION %s", e.what());
    } catch (...) {
        LOGD("Catched unknown exception (...)");
    }
}
}
