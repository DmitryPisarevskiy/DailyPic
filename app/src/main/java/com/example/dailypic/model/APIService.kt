package com.example.dailypic.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String) : Call<PictureServerResponseData>
}