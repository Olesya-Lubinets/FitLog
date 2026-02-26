package com.example.fitlog.data.api

import android.util.Log
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthCredentialsInterceptor(private val basicAuth: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
                .request().newBuilder()
                .addHeader("Authorization", basicAuth)
                .build()
        Log.d("AuthCredentialsInterceptor", "Credential for getting token have been added REQUEST URL = ${request.url}")
        return chain.proceed(request)
    }

}

object AuthRetrofit {

    private const val BASE_URL = "https://oauth.fatsecret.com/"

    private val basicAuth = Credentials.basic(ClientCredentials.clientID, ClientCredentials.clientSecret)

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(AuthCredentialsInterceptor(basicAuth))
        .build()

    val authAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthAPIService::class.java)
    }
}