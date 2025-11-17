package com.poly.sample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.poly.opencv.OpencvLib
import org.junit.Test
import org.junit.runner.RunWith
import org.opencv.android.Utils.bitmapToMat
import org.opencv.android.Utils.matToBitmap
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.RuntimeEnvironment.application
import org.robolectric.RuntimeEnvironment.getApplication
import org.robolectric.annotation.GraphicsMode
import java.io.InputStream

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)  // Rendering natif pour diffs exacts
class GoldenTest {

    @Test
    fun testAdaptiveThreshold() {
        val context: Context = getApplication()

        context.assets.open("input/lenna_input.png").use { inputStream: InputStream ->
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
        }
    }
}