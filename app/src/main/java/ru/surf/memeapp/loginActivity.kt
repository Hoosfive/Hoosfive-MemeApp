package ru.surf.memeapp

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*


class loginActivity : AppCompatActivity() {

    companion object {
        private const val TEXT_PASS = 0x00000091
        private const val TEXT_PASS_VISIBLE = 0x00000081
        private const val MIN_PASS_LENGTH = 8
        private const val PASS_HELPER_TEXT = "Пароль должен содержать $MIN_PASS_LENGTH символов"
        private const val EMPTY_FILEDS_ERROR = "Поле не может быть пустым"
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
        }
        passLine.setOnFocusChangeListener { view: View, b: Boolean ->
            passHelperText()
        }
        passLayout.setSimpleTextChangeWatcher { s: String, b: Boolean ->
            passHelperText()
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
            loginLayout.setError(EMPTY_FILEDS_ERROR,false)
        if(passLine.length() == 0)
            passLayout.setError(EMPTY_FILEDS_ERROR, false)
    }
    private fun passHelperText()
    {
        if (passLine.length() < MIN_PASS_LENGTH)
            passLayout.helperText = PASS_HELPER_TEXT
        else passLayout.helperText = " "
    }
}
