package com.example.texttoimageapp

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.http.POST
import okhttp3.ResponseBody
import retrofit2.http.*

interface TextToImageApi {
    @Multipart
    @POST("v2beta/stable-image/generate/ultra")
    suspend fun generateImage(
        @Part("prompt") prompt: RequestBody,
        @Part("output_format") outputFormat: RequestBody
    ): ResponseBody
}


object ApiClient {

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${Utils.API_KEY}")
                .addHeader("Accept", "image/*")
                .build()
            chain.proceed(request)
        }
        .build()

    val api: TextToImageApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.stability.ai/")
            .client(client)
            .build()
            .create(TextToImageApi::class.java)
    }
}

