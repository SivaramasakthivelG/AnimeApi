package com.example.animeapi

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.animeapi.ui.navigation.NavHostAnime
import com.example.animeapi.ui.theme.AnimeApiTheme
import com.example.animeapi.ui.view_model.AnimeListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeApiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    requestNotificationPermission()
                    val navController = rememberNavController()
                    NavHostAnime(navController,innerPadding)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission(){
        if(ContextCompat.checkSelfPermission(
                this,android.Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
            ){

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1001
            )
        }
    }
}

@SuppressLint("MissingInflatedId")
@Composable
fun XmlView(onFabClick: () -> Unit) {
    AndroidView(
        factory = { context ->
            val themedContext = ContextThemeWrapper(context, R.style.Theme_AnimeApi)
            val button = LayoutInflater.from(themedContext).inflate(R.layout.layout, null, false)
            button.findViewById<Button>(R.id.button).setOnClickListener {
                onFabClick()
            }
            button
        },
        modifier = Modifier
    )
}

