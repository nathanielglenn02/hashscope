package com.example.hashscope.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
        loadFragment(mainFragment)

        // Setup bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_main -> loadFragment(mainFragment)  // Memuat kembali MainFragment dengan kategori yang sama
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
