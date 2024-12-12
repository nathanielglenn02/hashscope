package com.example.hashscope.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hashscope.MainActivity
import com.example.hashscope.R
import com.example.hashscope.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan CATEGORY_ID dari Intent yang dikirimkan oleh CategoryFragment
        val categoryId = intent.getIntExtra("CATEGORY_ID", 0)

        // Inisialisasi fragment dengan argumen
        val mainFragment = MainFragment().apply {
            arguments = Bundle().apply { putInt("CATEGORY_ID", categoryId) }
        }
        val grafikFragment = GrafikFragment().apply {
            arguments = Bundle().apply { putInt("CATEGORY_ID", categoryId) }
        }
        val profilFragment = ProfilFragment()

        // Load MainFragment sebagai default
        if (savedInstanceState == null) {
            loadFragment(mainFragment)
        }

        // Setup bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_main -> loadFragment(mainFragment)
                R.id.nav_grafik -> loadFragment(grafikFragment)
                R.id.nav_profil -> loadFragment(profilFragment)
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        if (fragment != activeFragment) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right, R.anim.slide_out_left,
                    R.anim.slide_in_left, R.anim.slide_out_right
                ) // Tambahkan transisi animasi
                .replace(binding.fragmentContainer.id, fragment)
                .commit()
            activeFragment = fragment
        }
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(binding.fragmentContainer.id)

        if (currentFragment is MainFragment || currentFragment is GrafikFragment || currentFragment is ProfilFragment) {
            // Kembali ke CategoryFragment dengan efek transisi
            val intent = Intent(this, MainActivity::class.java) // MainActivity adalah tempat NavGraph Anda
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right) // Efek transisi
            finish()
        } else {
            super.onBackPressed() // Jika bukan salah satu fragment target, ikuti alur back default
        }
    }
}
