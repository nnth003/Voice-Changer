package com.thuanpx.mvvm_compose.utils.extension

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by ThuanPx on 23/04/2023.
 */


@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsEffect(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend (T) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        onEach(block).flowOn(context).launchIn(this)
    }
}