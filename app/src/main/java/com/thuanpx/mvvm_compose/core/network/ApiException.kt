package com.thuanpx.mvvm_compose.core.network

data class ApiException(
    val error: ErrorResponse?,
    val httpCode: Int,
    val httpMessage: String?
) : RuntimeException()
