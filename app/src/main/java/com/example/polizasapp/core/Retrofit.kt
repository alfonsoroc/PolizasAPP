package com.example.polizasapp.core

import com.example.polizasapp.viewmodel.WebService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {
    /*  private val okHttpClient = OkHttpClient.Builder()
          .addInterceptor { chain ->
              val request = chain.request()
              val newRequest = request.newBuilder()
                  .addHeader(
                      "Authorization",
                      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwOi8vY29wcGVsLmNvbSIsImF1ZCI6Imh0dHA6Ly9jb3BwZWwuY29tIiwiaWF0IjoxNjkyMjEzNTE4LCJuYmYiOjE2OTIyMTM1MTgsImV4cCI6MTY5MjI0MjYxOCwiYXBwSWQiOiI1OGNjYmEzNC02MzgyLTQ4MWQtYjg3Zi0zZmU3ZDk1ZDQzMGUiLCJlbWFpbCI6bnVsbCwidXNlciI6IjU4Y2NiYTM0LTYzODItNDgxZC1iODdmLTNmZTdkOTVkNDMwZSIsInVzZXJUeXBlIjoiMyIsImxvZ2luVHlwZSI6IjMiLCJkZXZpY2VJZCI6bnVsbCwicGFpcyI6Im1leCIsImV4dGVybm8iOmZhbHNlLCJpcCI6IjAuMC4wLjAiLCJkYXRhIjpudWxsfQ.9pyT5vNCLHXvBfiueeupyH8w1GKfz-XBPXeTSQCaO6Q"
                  )
                  .build()
              chain.proceed(newRequest)
          }
          .connectTimeout(50, TimeUnit.SECONDS)
          .readTimeout(50, TimeUnit.SECONDS)
          .writeTimeout(50, TimeUnit.SECONDS)
          .build()

  */


    fun createOkHttpClient(token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .addHeader(
                        "Authorization",
                        token
                    )
                    .build()
                chain.proceed(newRequest)
            }
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }


    fun createWebServicePolizas(token: String): WebService {
        return Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .client(createOkHttpClient(token))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WebService::class.java)
    }


    val okHttpCliente = OkHttpClient.Builder()
        .connectTimeout(50, TimeUnit.SECONDS)
        .readTimeout(50, TimeUnit.SECONDS)
        .writeTimeout(50, TimeUnit.SECONDS)
        .build()

    val webServiceToken: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(Constantes.GENERA_TOKEN)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpCliente)
            .build()
            .create(WebService::class.java)

    }
}