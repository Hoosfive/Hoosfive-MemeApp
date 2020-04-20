package ru.surf.memeapp

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("userDescription") val userDescription: String
) {

    companion object {
        val gson = Gson()
        fun deserialize(serializedData: String): UserInfo {
            return gson.fromJson(serializedData, UserInfo::class.java)
        }
    }

    fun serialize(): String {
        return gson.toJson(this)
    }
}