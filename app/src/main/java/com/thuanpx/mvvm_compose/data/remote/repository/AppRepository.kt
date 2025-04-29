package com.thuanpx.mvvm_compose.data.remote.repository

import com.thuanpx.mvvm_compose.core.network.flowTransform
import com.thuanpx.mvvm_compose.data.remote.api.ApiService
import com.thuanpx.mvvm_compose.di.IoDispatcher
import com.thuanpx.mvvm_compose.model.entity.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by ThuanPx on 17/09/2021.
 */

interface AppRepository {
    fun fetchPokemons(): Flow<List<Pokemon>>
}

class DefaultAppRepository @Inject constructor(
    private val apiService: ApiService,
) : AppRepository {

    override fun fetchPokemons(): Flow<List<Pokemon>> = flowTransform {
        apiService.fetchPokemons().data.orEmpty()
    }
}
