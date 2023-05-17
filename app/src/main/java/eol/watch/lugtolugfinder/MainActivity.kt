package eol.watch.lugtolugfinder

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting("Android")
        }
    }
}

@Composable
fun Greeting(name: String) {
    val colors = if (isSystemInDarkTheme()) {
        Log.d("checkDarkMode","다크 모드 확인...")
        darkColors(primary = Color.Black, onPrimary = Color.White)
    } else {
        lightColors(primary = Color.White, onPrimary = Color.Black)
    }

    MaterialTheme(colors = colors) {
        Text(text = "Hello $name!", color = MaterialTheme.colors.onPrimary)
    }
}

