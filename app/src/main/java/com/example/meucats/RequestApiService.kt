package com.example.meucats

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestApiService {
    @GET("/v1/images/search")
    fun getImageUrl(@Query("api_key") key: String): Call<MutableList<RequestData>>

    companion object Factory {
        fun create(): RequestApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.thecatapi.com/")
                .build()

            return retrofit.create(RequestApiService::class.java)
        }
    }
}