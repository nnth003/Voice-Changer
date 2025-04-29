package com.thuanpx.mvvm_compose.di

import com.thuanpx.mvvm_compose.data.remote.api.ApiService
import com.thuanpx.mvvm_compose.data.remote.repository.AppRepository
import com.thuanpx.mvvm_compose.data.remote.repository.DefaultAppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * Created by ThuanPx on 8/7/20.
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAppRepository(
        apiService: ApiService,
    ): AppRepository {
        return DefaultAppRepository(apiService)
    }
}
