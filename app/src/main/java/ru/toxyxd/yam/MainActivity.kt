package ru.toxyxd.yam

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import ru.toxyxd.player.MediaService
import ru.toxyxd.root.YaRootComponent
import ru.toxyxd.yam.ui.theme.YamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startService(Intent(this, MediaService::class.java))

        val root = YaRootComponent(
            componentContext = defaultComponentContext()
        )
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