package ru.surf.memeapp

import com.google.gson.annotations.SerializedName


data class ResponseBody (
    @SerializedName("accessToken") val accessToken : String,
    @SerializedName("userInfo") val userInfo : UserInfo
)