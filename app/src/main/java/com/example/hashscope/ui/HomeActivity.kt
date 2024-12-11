package com.example.hashscope.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hashscope.MainActivity
import com.example.hashscope.R
import com.example.hashscope.databinding.ActivityHomeBinding
import com.example.hashscope.ui.GrafikFragment
import com.example.hashscope.ui.ProfilFragment
import com.example.hashscope.ui.MainFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan CATEGORY_ID dari Intent yang dikirimkan oleh CategoryFragment
        val categoryId = intent.getIntExtra("CATEGORY_ID", 0)

        // Load MainFragment dengan CATEGORY_ID yang diterima
        val mainFragment = MainFragment().apply {
            arguments = Bundle().apply {
                putInt("CATEGORY_ID", categoryId) // Mengirimkan CATEGORY_ID ke MainFragment
            }
        }
        val grafikFragment = GrafikFragment().apply {
            arguments = Bundle().apply {
                putInt("CATEGORY_ID", categoryId) // Mengirimkan CATEGORY_ID ke GrafikFragment
            }
        }
        loadFragment(mainFragment)

        // Setup bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_main -> loadFragment(mainFragment)
                R.id.nav_grafik -> loadFragment(grafikFragment)
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

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(binding.fragmentContainer.id)

        if (currentFragment is MainFragment || currentFragment is GrafikFragment || currentFragment is ProfilFragment) {
            // Kembali ke CategoryFragment
            val intent = Intent(this, MainActivity::class.java) // MainActivity adalah tempat NavGraph Anda
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        } else {
            super.onBackPressed() // Jika bukan salah satu fragment target, ikuti alur back default
        }
    }


}
