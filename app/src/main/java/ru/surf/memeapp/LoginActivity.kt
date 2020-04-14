package ru.surf.memeapp

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
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
        //const val PREFS_DEFAULT_VALUE = "error"
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
       lateinit var userData : ResponseBody
        NetworkService.createInstance()
            .create(AuthApi::class.java)
            .postData(AuthBody(loginLine.text.toString(),passLine.text.toString()))
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    userData = response.body()!!
                    //Toast.makeText(this@LoginActivity, userData.accessToken + userData.userInfo, Toast.LENGTH_SHORT).show()
                    editPrefs(userData)
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val snack = Snackbar.make(loginBtnLayout, "Во время запроса произошла ошибка, возможно вы неверно ввели логин/пароль", Snackbar.LENGTH_LONG)
                    snack.view.setBackgroundColor(resources.getColor(R.color.colorSnackErrorLoginBackground))
                    snack.show()
                    t.printStackTrace()
                }
            })
    }
    private fun editPrefs(userData : ResponseBody)
    {
        val editor = pref.edit()
        editor.putString(AUTH_TOKEN_PREFERENCES, userData.accessToken)
        editor.putInt(ID_PREFERENCES,userData.userInfo.id)
        editor.putString(USERNAME_PREFERENCES,userData.userInfo.username)
        editor.putString(FIRSTNAME_PREFERENCES, userData.userInfo.firstName)
        editor.putString(LASTNAME_PREFERENCES,userData.userInfo.lastName)
        editor.putString(DESCRIPTION_PREFERENCES,userData.userInfo.userDescription)
        editor.apply()
        //testPrefs()
    }
    /*private fun testPrefs()
    {
        var string = StringBuilder()
        string.append(pref.getInt(ID_PREFERENCES,0).toString())
        string.append(pref.getString(USERNAME_PREFERENCES,PREFS_DEFAULT_VALUE))
        string.append(pref.getString(FIRSTNAME_PREFERENCES,PREFS_DEFAULT_VALUE))
        string.append(pref.getString(LASTNAME_PREFERENCES,PREFS_DEFAULT_VALUE))
        string.append(pref.getString(DESCRIPTION_PREFERENCES,PREFS_DEFAULT_VALUE))
        Snackbar.make(loginBtnLayout, "Во время запроса произошла ошибка, возможно вы неверно ввели логин/пароль", Snackbar.LENGTH_LONG).show()
        Snackbar.make(loginBtnLayout, string, Snackbar.LENGTH_LONG).show()

    }*/
}
