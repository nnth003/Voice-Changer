package com.pixelzlab.app.feature.home

import androidx.lifecycle.viewModelScope
import com.pixelzlab.app.core.base.BaseViewModel
import com.pixelzlab.app.data.remote.repository.AppRepository
import com.pixelzlab.app.di.IoDispatcher
import com.pixelzlab.app.model.entity.Pokemon
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
 * Created by pixelzlab on 23/04/2023.
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
