package com.mungai.data.remote

import com.mungai.common.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun newsSearch(
        @Query("apikey") apiKey: String = Constants.API_KEY,
        @Query("q") query: String,
        @Query("sortBy") sortBy: String = "popularity",
        @Query("searchIn") searchIn: String = "title,description",
        @Query("pageSize") pageSize: Int = 20
    ): ApiResponse
}