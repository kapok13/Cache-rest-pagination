package com.vb.cache_rest_pagination.data.network.service

import com.vb.cache_rest_pagination.data.network.model.RawgResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RawgService {

    @GET("api/games")
    suspend fun getTopGame(@Query("page") page: Int): RawgResponse

}