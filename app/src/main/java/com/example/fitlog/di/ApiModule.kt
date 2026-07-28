package com.example.fitlog.di

import com.example.fitlog.data.api.authenticationFoodApi.AuthAPIService
import com.example.fitlog.data.api.authenticationFoodApi.AuthCredentialsInterceptor
import com.example.fitlog.data.api.authenticationFoodApi.AuthTokenInterceptor
import com.example.fitlog.data.api.authenticationFoodApi.ClientCredentials
import com.example.fitlog.data.api.foodAPI.FoodApiService
import com.example.fitlog.data.api.workaoutAPI.WorkoutApiService
import com.example.fitlog.data.api.workaoutAPI.WorkoutTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    // ---------- Auth API ----------

    @Provides
    @Singleton
    @AuthOkHttpClient
    fun providesAuthOkHttpClient(): OkHttpClient {
        val basicAuth = Credentials.basic(
            ClientCredentials.clientID,
            ClientCredentials.clientSecret
        )
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(AuthCredentialsInterceptor(basicAuth))
            .build()
    }

    @Provides
    @Singleton
    @AuthRetrofit
    fun providesAuthRetrofit(@AuthOkHttpClient authOkHttpClient: OkHttpClient): Retrofit {
        val BASE_URL = "https://oauth.fatsecret.com/"

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(authOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesAuthAPIService(@AuthRetrofit authRetrofit: Retrofit): AuthAPIService {
        return authRetrofit.create(AuthAPIService::class.java)
    }

    // ---------- Food API ----------
    @Provides
    @Singleton
    @FoodOkHttpClient
    fun providesFoodOkHttpClient(authAPIService: AuthAPIService): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        return OkHttpClient.Builder()
            .addInterceptor(AuthTokenInterceptor(authAPIService))
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    @FoodRetrofit
    fun providesFoodRetrofit(@FoodOkHttpClient foodOkHttpClient: OkHttpClient): Retrofit {
        val BASE_URL = "https://platform.fatsecret.com/"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(foodOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesFoodApiService(@FoodRetrofit foodRetrofit: Retrofit): FoodApiService {
        return foodRetrofit.create(FoodApiService::class.java)
    }

    // ---------- Workout API ----------

    @Provides
    @Singleton
    @WorkoutOkHttpClient
    fun providesWorkoutOkHttpClient(): OkHttpClient {

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(WorkoutTokenInterceptor())
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    @WorkoutRetrofit
    fun providesWorkoutRetrofit(@WorkoutOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        val BASE_URL = "https://api.api-ninjas.com/"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesWorkoutAPIService(@WorkoutRetrofit workoutRetrofit: Retrofit): WorkoutApiService =
        workoutRetrofit.create(WorkoutApiService::class.java)

}