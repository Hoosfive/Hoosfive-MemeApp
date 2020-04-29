package ru.surf.memeapp.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponseBody(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("userInfoResponseBody") val userInfoResponseBody: UserInfoResponseBody
)