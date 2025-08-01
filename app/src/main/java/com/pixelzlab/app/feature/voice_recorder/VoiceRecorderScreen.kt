package com.pixelzlab.app.feature.voice_recorder

import android.view.View
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavHostController
import com.pixelzlab.app.feature.audio_effect.navigation.navigateToApplyEffect
import com.pixelzlab.app.feature.voice_recorder.fragment.VoiceRecorderFragment

@Composable
fun VoiceRecorderScreen(
    navController: NavHostController
) {
//    val context = LocalContext.current
    val view = LocalView.current
    val fragmentManager = (view.context as FragmentActivity).supportFragmentManager
    val fragmentId = remember { View.generateViewId() }
//    val currentTab by remember { mutableStateOf(VoiceTab.Recorder) }
    val isRecording = remember { mutableStateOf(false) }
    val showExitDialog = remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        if (isRecording.value) {
            showExitDialog.value = true
        } else {
            navController.popBackStack()
        }
    }

    if (showExitDialog.value) {
        AlertDialog(
            onDismissRequest = { showExitDialog.value = false },
            title = { Text("Xác nhận thoát") },
            text = { Text("Bạn đang ghi âm. Nếu thoát, bản ghi sẽ bị mất. Bạn có chắc muốn thoát?") },
            confirmButton = {
                androidx.compose.material3.TextButton(onClick = {
                    showExitDialog.value = false
                    navController.popBackStack()
                }) { Text("Thoát") }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(onClick = {
                    showExitDialog.value = false
                }) { Text("Ở lại") }
            }
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Trình ghi âm") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (isRecording.value) showExitDialog.value = true
                            else navController.popBackStack()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
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
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    FragmentContainerView(ctx).apply {
                        id = fragmentId
                        if (fragmentManager.findFragmentById(id) == null) {
                            val fragment = VoiceRecorderFragment()
                            fragment.onNavigationApplyEffect = { filePath ->
                                navController.navigateToApplyEffect(filePath)
                            }
                            fragment.onRecordingStateChanged = { recording ->
                                isRecording.value = recording
                            }
                            fragmentManager.beginTransaction().add(id, fragment).commit()
                        }

                    }
                }
            )
        }

    }

}
