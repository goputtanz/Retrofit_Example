package com.example.retrofit_example


import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("random")
    fun getAnimeList (): Call<AnimeResponse>

}