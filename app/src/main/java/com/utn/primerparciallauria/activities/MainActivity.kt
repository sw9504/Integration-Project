package com.utn.primerparciallauria.activities

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utn.primerparciallauria.R


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var navHostFragment : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settings = PreferenceManager.getDefaultSharedPreferences(this)
        val colorList = settings.getString("colorList","purple")

        when(colorList){
            "PurpleTheme" -> setTheme(R.style.PurpleTheme)
            "WhiteTheme" -> setTheme(R.style.WhiteTheme)
            "GrayTheme" -> setTheme(R.style.GrayTheme)
        }

        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        bottomNavView = findViewById(R.id.bottomBar)
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }
}