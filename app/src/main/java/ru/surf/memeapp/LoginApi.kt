package ru.surf.memeapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/auth/login")
    fun login(@Body auth: AuthBody): Call<LoginResponseBody>
}
