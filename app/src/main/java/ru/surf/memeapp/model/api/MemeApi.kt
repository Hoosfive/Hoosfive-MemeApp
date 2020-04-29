package ru.surf.memeapp.model.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.surf.memeapp.domain.AuthBody
import ru.surf.memeapp.model.response.LoginResponseBody
import ru.surf.memeapp.model.response.MemesResponseBody

interface MemeApi {

    @POST("/auth/login")
    fun login(@Body auth: AuthBody): Call<LoginResponseBody>

    @GET("/memes")
    fun getMemes(): Call<List<MemesResponseBody>>
}
