package ru.surf.memeapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent = Intent(this, loginActivity::class.java)
        Handler().postDelayed({
            startActivity(intent)
            this.finish()
        }, 300)
    }
}
