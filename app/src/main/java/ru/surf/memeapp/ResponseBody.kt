package ru.surf.memeapp

import com.google.gson.annotations.SerializedName


class ResponseBody {
    @SerializedName("accessToken") val accessToken : String? = null
    @SerializedName("userInfo") val userInfo : UserInfo? = null
}