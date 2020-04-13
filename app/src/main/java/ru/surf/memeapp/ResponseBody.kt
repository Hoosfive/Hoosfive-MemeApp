package ru.surf.memeapp

import com.google.gson.annotations.SerializedName


class ResponseBody {
    @SerializedName("accessToken")
    private var accessToken: String? = null

    companion object{
        fun getAccessToken(responseBody : ResponseBody): String? {
            return responseBody.accessToken
        }
    }
}