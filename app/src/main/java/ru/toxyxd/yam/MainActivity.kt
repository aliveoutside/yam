package ru.toxyxd.yam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.arkivanov.decompose.defaultComponentContext
import ru.toxyxd.root.YaRootComponent
import ru.toxyxd.yam.ui.theme.YamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val root =
            YaRootComponent(
                componentContext = defaultComponentContext()
            )

        setContent {
            YamTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentView(rootComponent = root)
                }
            }
        }
    }
}