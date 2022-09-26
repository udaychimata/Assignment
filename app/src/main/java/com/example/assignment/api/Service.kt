package com.example.assignment.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import com.example.assignment.data.Item

/**
 * Used to connect to the API to fetch data
 */
interface Service {

    @GET("/v3/launches")
    suspend fun getListItems(
    ): List<Item>

    companion object {
        private const val BASE_URL = "https://api.spacexdata.com"
        fun create(): Service {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Service::class.java)
        }
    }
}