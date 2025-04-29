package com.thuanpx.mvvm_compose.core.network

import com.squareup.moshi.Json

/**
 * Created by ThuanPx on 23/04/2023.
 */
data class ErrorResponse(
    @Json(name = "message")
    val message: String
)