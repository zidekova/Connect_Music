package com.example.connectmusic

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.connectmusic.ui.theme.ConnectMusicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate: Start")
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate: After super.onCreate")
        setContent {
            Log.d("MainActivity", "onCreate: setContent")
            ConnectMusicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConnectMusicApp()
                }
            }
        }
        Log.d("MainActivity", "onCreate: End")
    }
}