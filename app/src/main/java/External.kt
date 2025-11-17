package com.poly.opencv

import org.opencv.core.Mat

object OpencvLib {
    init {
        System.loadLibrary("OpencvLib")
    }

    external fun stringFromJNI(): String
    external fun adaptiveThreshold(addrSrc: Long, addrDst: Long)

    fun adaptiveThreshold(inputMat: Mat, outputMat: Mat) {
        adaptiveThreshold(inputMat.nativeObj, outputMat.nativeObj)
    }
}