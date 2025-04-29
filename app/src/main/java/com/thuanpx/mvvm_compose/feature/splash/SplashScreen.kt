package com.thuanpx.mvvm_compose.feature.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thuanpx.mvvm_compose.R
import com.thuanpx.mvvm_compose.core.PhoneDevicePreviews
import com.thuanpx.mvvm_compose.core.navigation.AppDestination
import com.thuanpx.mvvm_compose.designsystem.theme.AppTheme
import kotlinx.coroutines.delay

/**
 * Created by ThuanPx on 02/01/2023.
 */

@Composable
fun SplashRoute(
    navigator: (destination: AppDestination) -> Unit
) {
    SplashScreen(
        navigator = navigator,
    )
}

@Composable
fun SplashScreen(
    navigator: (destination: AppDestination) -> Unit,
) {
    var isShow by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = Unit, block = {
        delay(1_000)
        isShow = false
        delay(500)
        navigator(AppDestination.Home)
    })
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AnimatedVisibility(
                visible = isShow,
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                )
            }
        }
    }
}

@PhoneDevicePreviews
@Composable
fun PreviewLoginScreen() {
    AppTheme {
        SplashRoute {}
    }
}

