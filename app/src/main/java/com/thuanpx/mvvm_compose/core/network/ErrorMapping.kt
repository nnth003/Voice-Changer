package com.thuanpx.mvvm_compose.core.network

import android.content.Context
import com.thuanpx.mvvm_compose.R

fun Throwable.message(context: Context): String {
    return when (this) {
        is ApiException -> error?.message
        else -> message
    } ?: context.getString(R.string.error_generic)
}
