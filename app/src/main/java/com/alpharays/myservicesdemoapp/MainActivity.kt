package com.alpharays.myservicesdemoapp

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.alpharays.myservicesdemoapp.foregroundservice.MyForegroundService
import com.alpharays.myservicesdemoapp.ui.theme.MyServicesDemoAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        enableEdgeToEdge()
        setContent {
            MyServicesDemoAppTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        Intent(applicationContext, MyForegroundService::class.java).also {
                            it.action =
                                MyForegroundService.MyForegroundServiceActions.START.toString()
                            startService(it)
                        }
                    }) {
                        Text(text = "Start foreground service")

                    }
                    Button(onClick = {
                        Intent(applicationContext, MyForegroundService::class.java).also {
                            it.action =
                                MyForegroundService.MyForegroundServiceActions.STOP.toString()
                            startService(it)
                        }
                    }) {
                        Text(text = "Stop foreground service")

                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyServicesDemoAppTheme {
        Greeting("Android")
    }
}