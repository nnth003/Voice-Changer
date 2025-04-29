package com.thuanpx.mvvm_compose.data.remote.api

import com.thuanpx.mvvm_compose.model.base.BaseResponse
import com.thuanpx.mvvm_compose.model.entity.Pokemon
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by ThuanPx on 8/5/20.
 */
interface ApiService {

    @GET("pokemon")
    suspend fun fetchPokemons(
        @Query("limit") limit: Int = 20,
        @Query("offset") page: Int = 0
    ): BaseResponse<List<Pokemon>>

}
