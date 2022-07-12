package com.instacart.android.challenges.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {

    val okhttplogging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okhttp = OkHttpClient.Builder()
        .addInterceptor(okhttplogging)
        .build()

    private val retrofit: Retrofit = Builder()
        .baseUrl("https://boiling-dusk-12902.herokuapp.com/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttp)
        .build()

    val api: NetworkApi = retrofit.create(NetworkApi::class.java)
}