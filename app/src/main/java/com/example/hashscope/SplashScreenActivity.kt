package com.example.hashscope

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val logo = findViewById<ImageView>(R.id.logo)
        val appName = findViewById<TextView>(R.id.app_name)

        // Animasi Fade In
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 1000 // 1 detik
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                // Pindah ke MainActivity setelah animasi selesai
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out) // Tambahkan transisi
                    finish()
                }, 2000) // Tambahan delay 1 detik
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        // Terapkan animasi
        logo.startAnimation(fadeIn)
        appName.startAnimation(fadeIn)
    }
}
