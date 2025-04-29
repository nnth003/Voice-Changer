package com.pixelzlab.app.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixelzlab.app.core.navigation.AppDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel() {

    private var loadingCount: Int = 0

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    protected val _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?> = _error

    protected val _navigator = MutableSharedFlow<AppDestination>()
    val navigator: SharedFlow<AppDestination> = _navigator

    /**
     * To show loading manually, should call `hideLoading` after
     */
    protected fun showLoading() {
        if (loadingCount == 0) {
            _isLoading.value = true
        }
        loadingCount++
    }

    /**
     * To hide loading manually, should be called after `showLoading`
     */
    protected fun hideLoading() {
        loadingCount--
        if (loadingCount == 0) {
            _isLoading.value = false
        }
    }

    protected fun launch(context: CoroutineContext = EmptyCoroutineContext, job: suspend () -> Unit) =
        viewModelScope.launch(context) {
            job.invoke()
        }

    protected fun <T> Flow<T>.loading(): Flow<T> = this
        .onStart { showLoading() }
        .onCompletion { hideLoading() }

    suspend fun <T> Flow<T>.async(
        action: suspend (T) -> Unit
    ) {
        this.loading()
            .catch {
                _error.emit(it)
            }
            .collect {
                action.invoke(it)
            }
    }
}
