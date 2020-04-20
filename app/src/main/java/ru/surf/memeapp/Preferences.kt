package ru.surf.memeapp

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Preferences {
    companion object {
        private const val USER_DATA_PREFERENCES = "UserData"
        private const val AUTH_TOKEN_PREFERENCES = "auth_token"
        private const val USER_INFO_PREFERENCES = "user_info"
        private const val DEF_VALUE = "no_data_found"
        private lateinit var editor: SharedPreferences.Editor

        private fun getPref(context: Context): SharedPreferences {
            return context.getSharedPreferences(USER_DATA_PREFERENCES, MODE_PRIVATE)
        }

        fun editAuthTokenPref(context: Context, authToken: String) {
            editor = getPref(context).edit()
            editor.putString(AUTH_TOKEN_PREFERENCES, authToken)
            editor.apply()
        }

        fun editUserInfoPrefs(context: Context, userInfo: UserInfo) {
            editor = getPref(context).edit()
            editor.putString(USER_INFO_PREFERENCES, userInfo.serialize())
            editor.apply()
        }

        fun getAuthTokenPref(context: Context): String? {
            return getPref(context).getString(AUTH_TOKEN_PREFERENCES, DEF_VALUE)
        }

        fun getUserInfoPrefs(context: Context): UserInfo {
            return UserInfo.deserialize(
                getPref(context).getString(
                    USER_INFO_PREFERENCES,
                    DEF_VALUE
                ).toString()
            )
        }
    }


}