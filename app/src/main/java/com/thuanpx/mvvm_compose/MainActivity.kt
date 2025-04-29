package com.thuanpx.mvvm_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.thuanpx.mvvm_compose.core.navigation.AppNavigation
import com.thuanpx.mvvm_compose.designsystem.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.White.toArgb(),
                Color.White.toArgb(),
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.White.toArgb(),
                Color.White.toArgb(),
            )
        )
        setContent {
            DesignSystemCatalogApp()
        }
    }
}

@Composable
fun MyApp() {
    AppTheme {
        val navController = rememberNavController()
        Scaffold { padding ->
            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(padding)
            )
        }
    }
}
