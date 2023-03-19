package com.example.eexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView

class SplashScreen : AppCompatActivity() {

    lateinit var tvLucky: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        tvLucky = findViewById(R.id.txt_lucky)

        tvLucky.translationY = 1000f
        tvLucky.animate().translationY(0f).duration = 1500

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,LogInActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}