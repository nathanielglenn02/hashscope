package com.example.hashscope.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hashscope.R

class DetailTopicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_topic)

        // Get the selected platform from the intent
        val selectedPlatform = intent.getStringExtra("SELECTED_PLATFORM")

        // Load the appropriate fragment
        when (selectedPlatform) {
            "Aplikasi X" -> loadFragment(DetailXFragment())
            "YouTube" -> loadFragment(DetailYoutubeFragment())
            "Web (Browser)" -> loadFragment(DetailWebFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
