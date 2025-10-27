package com.poly.opencv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Exemple d'appel à une méthode native implémentée par la lib 'opencv'
        setContent {
            OpenCVTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(stringFromJNI())
                }
            }
        }
    }

    /**
     * Une méthode native implémentée par la lib 'opencv', qui est packagée avec l'app.
     */
    external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("opencv")
        }
    }
}

@Composable
fun Greeting(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OpenCVTheme {
        Greeting("Android avec OpenCV et Compose !")
    }
}