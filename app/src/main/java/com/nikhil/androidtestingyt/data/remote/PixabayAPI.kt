package com.nikhil.androidtestingyt.data.remote

import com.nikhil.androidtestingyt.BuildConfig
import com.nikhil.androidtestingyt.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery : String,
        @Query("key") apiKey : String = BuildConfig.API_KEY
        ) : Response<ImageResponse>

}