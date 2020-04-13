package ru.surf.memeapp

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TEXT_PASS = 0x00000091
        private const val TEXT_PASS_VISIBLE = 0x00000081
        private const val MIN_PASS_LENGTH = 8
        private const val PASS_HELPER_TEXT = "Пароль должен содержать $MIN_PASS_LENGTH символов"
        private const val EMPTY_FIELDS_ERROR = "Поле не может быть пустым"
        const val USERDATA_PREFERENCES = "UserData"
        const val AUTH_TOKEN_PREFERENCES = "auth_token"
        const val ID_PREFERENCES = "id"
        const val USERNAME_PREFERENCES = "username"
        const val FIRSTNAME_PREFERENCES = "firstName"
        const val LASTNAME_PREFERENCES = "lastName"
        const val DESCRIPTION_PREFERENCES = "userDescription"
        lateinit var pref: SharedPreferences


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListeners()
        pref = getSharedPreferences(USERDATA_PREFERENCES, MODE_PRIVATE)

    }
    private fun initListeners()
    {
        passLayout.endIconImageButton.setOnClickListener {
            passwordVisibleToggle()
        }
        loginBtn.setOnClickListener {
            showLoading()
            validateFields()
            getAuthToken()
        }
        passLine.setOnFocusChangeListener { view: View, b: Boolean ->
            passHelperTextVisible()
        }
        passLayout.setSimpleTextChangeWatcher { s: String, b: Boolean ->
            passHelperTextVisible()
        }
    }
    private fun passwordVisibleToggle()
    {
        val selection = passLine.selectionEnd
        if (passLine.inputType == TEXT_PASS)
        {
            passLine.inputType = TEXT_PASS_VISIBLE
            passLine.setSelection(selection)
            passLayout.endIconImageButton.setImageResource(R.drawable.ic_eye)
        }
        else
        {
            passLine.inputType = TEXT_PASS
            passLine.setSelection(selection)
            passLayout.endIconImageButton.setImageResource(R.drawable.ic_eye_off)
        }
    }
    private fun showLoading()
    {
        loginBtn.isEnabled = false
        loginBtn.showLoading()
        Handler().postDelayed({
            loginBtn.hideLoading()
            loginBtn.isEnabled = true
        }, 1500)
    }
    private fun validateFields()
    {
        if (loginLine.length() == 0)
            loginLayout.setError(EMPTY_FIELDS_ERROR,false)
        if(passLine.length() == 0)
            passLayout.setError(EMPTY_FIELDS_ERROR, false)
    }
    private fun passHelperTextVisible()
    {
        if (passLine.length() < MIN_PASS_LENGTH)
            passLayout.helperText = PASS_HELPER_TEXT
        else passLayout.helperText = " "
    }
    private fun getAuthToken()
    {
        val auth = AuthBody()
        auth.login = loginLine.text.toString()
        auth.password = passLine.text.toString()
        var userDsta = ResponseBody()
        NetworkService.getInstance()
            .jsonApi
            .postData(auth)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    userDsta = response.body()!!
                //    Toast.makeText(this@LoginActivity, userDsta.accessToken + userDsta.userInfo, Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        editPrefs(userDsta)
    }
    private fun editPrefs(userData : ResponseBody)
    {
        val editor = pref.edit()
        editor.putString(AUTH_TOKEN_PREFERENCES, userData.accessToken)
        userData.userInfo?.id?.let { editor.putInt(ID_PREFERENCES, it) }
        userData.userInfo?.username?.let { editor.putString(USERNAME_PREFERENCES, it) }
        userData.userInfo?.firstName?.let { editor.putString(FIRSTNAME_PREFERENCES, it) }
        userData.userInfo?.lastName?.let { editor.putString(LASTNAME_PREFERENCES, it) }
        userData.userInfo?.userDescription?.let { editor.putString(DESCRIPTION_PREFERENCES, it) }
        editor.apply()
    }
}
