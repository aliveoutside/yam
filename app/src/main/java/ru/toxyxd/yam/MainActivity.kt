package ru.toxyxd.yam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.retainedComponent
import ru.toxyxd.root.YaRootComponent
import ru.toxyxd.yam.ui.theme.YamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = retainedComponent { componentContext ->
            YaRootComponent(
                componentContext = componentContext
            )
        }
        setContent {
            YamTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentView(rootComponent = root)
                }
            }
        }
    }
}