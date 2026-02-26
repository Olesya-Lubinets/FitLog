package com.example.fitlog.data.api

import com.example.fitlog.data.model.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthAPIService {

    @FormUrlEncoded
    @POST("connect/token")
    suspend fun getToken(
        @Field("grant_type") grant_type :String = "client_credentials",
        @Field("scope") scope:String = "basic"
    ):TokenResponse
}