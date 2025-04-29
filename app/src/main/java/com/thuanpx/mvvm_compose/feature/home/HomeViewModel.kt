package com.thuanpx.mvvm_compose.feature.home

import androidx.lifecycle.viewModelScope
import com.thuanpx.mvvm_compose.core.base.BaseViewModel
import com.thuanpx.mvvm_compose.data.remote.repository.AppRepository
import com.thuanpx.mvvm_compose.di.IoDispatcher
import com.thuanpx.mvvm_compose.model.entity.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by ThuanPx on 23/04/2023.
 */
@HiltViewModel
class HomeViewModel @Inject constructor (
    private val appRepository: AppRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BaseViewModel() {

    private val _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemons = _pokemons.asStateFlow()

    init {
        appRepository.fetchPokemons()
            .loading()
            .onEach {
                _pokemons.emit(it)
            }
            .flowOn(ioDispatcher)
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)
    }

}
