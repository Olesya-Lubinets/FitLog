package com.example.fitlog.data.api

import android.util.Log
import kotlinx.coroutines.runBlocking

object TokenManager {

    private var token:String? = null
    private var expiresAt : Long = 0

    fun getToken(): String? {
        return token
    }

    private fun saveToken(newToken:String, newExpiresIn:Int) {
        token = newToken
        expiresAt = System.currentTimeMillis()+ (newExpiresIn*1000)
    }

     fun fetchTokenIfNeeded(authAPIService: AuthAPIService) {
        if (token==null || checkIfTokenExpired()) {
            runBlocking {
                val  tokenResponse = authAPIService.getToken()
                saveToken(tokenResponse.access_token,tokenResponse.expires_in)  }
            Log.d("Token Manager", "Token has been updated")
        }
    }

    private fun checkIfTokenExpired():Boolean =  System.currentTimeMillis() >= expiresAt

}
