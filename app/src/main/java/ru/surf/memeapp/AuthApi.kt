package ru.surf.memeapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth/login")
    fun postData(@Body auth: AuthBody): Call<ResponseBody>
}
