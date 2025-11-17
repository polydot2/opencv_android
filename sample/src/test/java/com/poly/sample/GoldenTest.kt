package com.poly.sample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.poly.opencv.OpencvLib
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils.bitmapToMat
import org.opencv.android.Utils.matToBitmap
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)  // Rendering natif pour diffs exacts
class GoldenTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun loadLibraries() {
            val opencvLoaded = OpenCVLoader.initLocal()
            Assert.assertTrue(
                "ÉCHEC CRITIQUE: La bibliothèque native d'OpenCV (libopencv_java4.so) n'a pas pu être chargée.",
                opencvLoaded
            )
//            System.loadLibrary("OpencvLib")
        }
    }

    @Test
    fun testAdaptiveThreshold() {
        val inputStream = GoldenTest::class.java.classLoader?.getResourceAsStream("input/lenna_input.png")

        val inputBitmap = BitmapFactory.decodeStream(inputStream)

        // Créer Mats (CV_8UC1 pour gray, ou CV_8UC3 pour color)
        val matInput = Mat(Size(inputBitmap.width.toDouble(), inputBitmap.height.toDouble()), CvType.CV_8UC1)
        val matOutput = Mat(Size(inputBitmap.width.toDouble(), inputBitmap.height.toDouble()), CvType.CV_8UC1)

        // Bitmap to Mat
        bitmapToMat(inputBitmap, matInput)

        // Run JNI
        OpencvLib.adaptiveThreshold(matInput.nativeObj, matOutput.nativeObj)

        // Mat to Bitmap
        val outputBitmap = Bitmap.createBitmap(matOutput.cols(), matOutput.rows(), Bitmap.Config.ARGB_8888)
        matToBitmap(matOutput, outputBitmap)

        // Release Mats
        matInput.release()
        matOutput.release()

        outputBitmap.captureRoboImage(
            filePath = "Lenna_output",
            roborazziOptions = RoborazziOptions(
                compareOptions = RoborazziOptions.CompareOptions(changeThreshold = 0.02f)
            )
        )

        inputStream?.close()
    }
}