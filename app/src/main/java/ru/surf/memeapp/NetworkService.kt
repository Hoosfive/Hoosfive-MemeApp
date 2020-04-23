package ru.surf.memeapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService private constructor() {
    companion object {
        private const val BASE_URL = "http://demo2407529.mockable.io"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val loginApi = retrofit.create(LoginApi::class.java)

        fun auth(
            login: String,
            password: String,
            onSuccess: (LoginResponseBody) -> Unit,
            onError: (Throwable) -> Unit = { }
        ) {
            loginApi
                .login(AuthBody(login, password))
                .enqueue(
                    RetrofitCallback(
                        { data -> onSuccess(data) },
                        { error -> onError(error) }
                    )
                )
        }
    }
}