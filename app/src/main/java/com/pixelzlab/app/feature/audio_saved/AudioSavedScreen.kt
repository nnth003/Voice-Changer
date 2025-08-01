package com.pixelzlab.app.feature.audio_saved

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavHostController
import com.pixelzlab.app.feature.home_voice.navigation.navigateToHomeVoice

@Composable
fun AudioSavedScreen(
    navHostController: NavHostController,
    savedFilePath: String,
) {
    val view = LocalView.current
    val fragmentManager = (view.context as FragmentActivity).supportFragmentManager
    val fragmentId = remember { View.generateViewId() }
    LaunchedEffect(savedFilePath) {
        Log.d("AudioSavedScreen", "Received saved file path: $savedFilePath")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Lưu hiệu ứng") },
                navigationIcon = {
                    IconButton(onClick = {
                        val fragment = fragmentManager.findFragmentById(fragmentId)
                        navHostController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navHostController.navigateToHomeVoice()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = ""
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize(),
                factory = { ctx ->
                    FragmentContainerView(ctx).apply {
                        id = fragmentId
                        if (fragmentManager.findFragmentById(id) == null) {
                            val fragment = AudioSavedFragment()
                            fragment.apply {
                                arguments = Bundle().apply {
                                    putString("saved_file_path", savedFilePath)
                                }
                            }
                            fragmentManager.beginTransaction().replace(id, fragment).commit()
                        }
                    }
                }
            )
        }

    }
}