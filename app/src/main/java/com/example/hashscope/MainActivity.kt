package com.example.hashscope

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.hashscope.preference.UserPreference

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController: NavController = navHostFragment.navController

            val userPreference = UserPreference(this)
            val token = userPreference.getToken()

            if (token != null) {
                // Jika token ada, navigasi ke CategoryFragment
                navController.navigate(R.id.categoryFragment)
            } else {
                // Jika token tidak ada, navigasi ke LoginFragment
                navController.navigate(R.id.loginFragment)
            }
        }
    }
}
