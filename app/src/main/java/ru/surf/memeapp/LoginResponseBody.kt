package ru.surf.memeapp

import com.google.gson.annotations.SerializedName

data class LoginResponseBody(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("userInfo") val userInfo: UserInfo
)