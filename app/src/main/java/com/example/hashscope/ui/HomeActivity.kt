package com.example.hashscope.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hashscope.R
import com.example.hashscope.databinding.ActivityHomeBinding
import com.example.hashscope.ui.fragments.MainFragment
import com.example.hashscope.ui.fragments.GrafikFragment
import com.example.hashscope.ui.fragments.ProfilFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load default fragment
        loadFragment(MainFragment())

        // Setup bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_main -> loadFragment(MainFragment())
                R.id.nav_grafik -> loadFragment(GrafikFragment())
                R.id.nav_profil -> loadFragment(ProfilFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}
