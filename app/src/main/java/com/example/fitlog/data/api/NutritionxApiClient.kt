package com.example.fitlog.data.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object FitSecretApiClient {

    private const val ConsumerSecret = "ac19b152b369476986bea07c6550582c"
    private const val ConsumerKey = "012aac55d6914cb4a841a6aa42042d06"
    private const val TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijk0Q0NGMUU4RTI4RUM3RkM4QTc2RTcyNTM4M0E4RjM0OUU3MUUzRjAiLCJ0eXAiOiJhdCtqd3QiLCJ4NXQiOiJsTXp4Nk9LT3hfeUtkdWNsT0RxUE5KNXg0X0EifQ.eyJuYmYiOjE3NjgyMjgzOTYsImV4cCI6MTc2ODMxNDc5NiwiaXNzIjoiaHR0cHM6Ly9vYXV0aC5mYXRzZWNyZXQuY29tIiwiYXVkIjoiYmFzaWMiLCJjbGllbnRfaWQiOiIwMTJhYWM1NWQ2OTE0Y2I0YTg0MWE2YWE0MjA0MmQwNiIsInNjb3BlIjpbImJhc2ljIl19.xFj7EwUWdCF1QB22rbU5AMJv2Jp1VdbfgMhJaXWT2BULQE9vmbw-UtZgDnWBtK-hLgPHkC7_n23N8u_nE7vjKdLA9iz01hUkkjU5JIpQ-zSYv4tslo85wzRhhNJ5wXgqMEr1DGow5s5U7uiXUhV7qK_lutife0qEul_aGePd-F0EQN0dpkK3ICsAHumZyY-nFmEDrOdBE70tcRVJBltBWjaMiN-2ANoOqNNQf9QN1P77G9NfMstbL4Lj2nTKTwDe_vY8TR34RupndV9CWcpZTR1QNh4jQxfV1eYD33Ms_6WmhhcSnoy-1lH0T9uE3zF76qG_asO_l1el6P91DurKWZ522gn3wdl98OtCNxodyYndZfwTQCLGY4d8NQ9JkkdwGGoUNZ9CrAnszuTlxZw3zOuR7hBUhzXJ_sTu-SYEMnwP62Y06yqR3LJMn4xnqb32JHYrtw3IPNAhTFxrKSeBzXGGDAdjz3tdpHn1V4z91eynGDROripUn0vqARKA9l7n-oMPEfBH25WhzHEt9aOgbGtPIbYcyNZWUZtfuJZ_YX5RgyXCenCb2aKgGzV78PTfU255QxCgkCE5hXYORJg0-ZwltUUFzBHHgZUSNFmkfocTOs9lgrAWab-9Jn5jZFV5gfB0UrwizHFjcHP89f1TqgAiTCij03HcfWbbIDx_Ic8"
    private const val BASE_URL = "https://platform.fatsecret.com/rest/"

    class AuthInterceptor(
        private val accessToken: String
    ) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
            Log.d("FoodViewModel", "REQUEST URL = ${request.url}")
            return chain.proceed(request)
        }
    }

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(TOKEN))
        .addInterceptor(logging)
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val foodApi: FoodApiService = retrofit.create(FoodApiService::class.java)
    val workoutApi:WorkoutApiService = retrofit.create(WorkoutApiService::class.java)
}
