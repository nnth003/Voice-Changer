package com.pixelzlab.app.feature.audio_effect

import android.view.View
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavHostController
import com.pixelzlab.app.R
import com.pixelzlab.app.feature.audio_effect.fragment.AudioEffectFragment
import com.pixelzlab.app.feature.audio_saved.navigateAudioSaved
import kotlinx.coroutines.launch

@Composable
fun AudioEffectScreen(
    navHostController: NavHostController,
    audioFilePath: String
) {
    val view = LocalView.current
    val fragmentManager = (view.context as FragmentActivity).supportFragmentManager
    val fragmentId = remember { View.generateViewId() }
    val activity = LocalView.current.context as FragmentActivity

    var showSaveDialog by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    var defaultFileName by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    if (showSaveDialog) {
        SaveFileNameDialog(
            initialName = defaultFileName,
            onDismiss = { showSaveDialog = false },
            onSave = { fileName ->
                showSaveDialog = false

                val progressView = activity.findViewById<View>(R.id.progress_loading)
                progressView?.visibility = View.VISIBLE

                val fragment = fragmentManager.findFragmentById(fragmentId)
                if (fragment is AudioEffectFragment) {
                    coroutineScope.launch {
                        try {
                            val savedFile = fragment.saveEffectedAudio(fileName)
                            fragmentManager.beginTransaction().remove(fragment).commit()
                            navHostController.navigateAudioSaved(savedFile.absolutePath)
                        } finally {
                            progressView?.visibility = View.GONE
                        }

                    }
                }
                showSaveDialog = false
            }
        )
    }

    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Thoát áp dụng hiệu ứng?") },
            text = { Text("Bạn có chắc muốn thoát không? Mọi thay đổi sẽ không được lưu.") },
            confirmButton = {
                Button(onClick = {
                    showExitDialog = false
                    val fragment = fragmentManager.findFragmentById(fragmentId)
                    if (fragment is AudioEffectFragment) {
                        fragment.stopAudio()
                        fragmentManager.beginTransaction().remove(fragment).commit()
                    }
                    navHostController.popBackStack()
                }) {
                    Text("Thoát")
                }
            },
            dismissButton = {
                Button(onClick = { showExitDialog = false }) {
                    Text("Ở lại")
                }
            }
        )
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Chọn hiệu ứng") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                        val fragment = fragmentManager.findFragmentById(fragmentId)
                        if (fragment is AudioEffectFragment) {
                            fragment.stopAudio() // Dừng phát nhạc
                        }
//
//                        fragmentManager.beginTransaction().remove(fragment!!).commit()
//                        navHostController.popBackStack()

                            showExitDialog = true
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val fragment = fragmentManager.findFragmentById(fragmentId)
                            if (fragment is AudioEffectFragment) {
                                fragment.stopAudio()

                                defaultFileName =
                                    "${fragment.currentEffect}_${System.currentTimeMillis()}.wav"
                                showSaveDialog = true
//                                // Apply effect & ghi file
//                                val savedFile = fragment.saveEffectedAudio()
//
//                                // Remove fragment
//                                fragmentManager.beginTransaction().remove(fragment).commit()
//
//                                // Navigate
//                                navHostController.navigateAudioSaved(savedFile.absolutePath)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Lưu"
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
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    FragmentContainerView(ctx).apply {
                        id = fragmentId
                        if (fragmentManager.findFragmentById(id) == null) {
                            fragmentManager.beginTransaction()
                                .add(id, AudioEffectFragment.newInstance(audioFilePath))
                                .commit()
                        }
                    }
                }
            )
        }
    }

}