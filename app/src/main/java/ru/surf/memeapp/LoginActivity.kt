package ru.surf.memeapp

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
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
        val auth = AuthBody()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListeners()
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
        auth.login = loginLine.text.toString()
        auth.password = passLine.text.toString()
        var string: String
        NetworkService.getInstance()
            .jsonApi
            .postData(auth)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val post = response.body()
                    string = (post?.let { ResponseBody.getAccessToken(it) }.toString())
                    Toast.makeText(this@LoginActivity, string, Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}
