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
                navController.navigate(R.id.categoryFragment)
            } else {
                navController.navigate(R.id.loginFragment)
            }
        }
    }
}
