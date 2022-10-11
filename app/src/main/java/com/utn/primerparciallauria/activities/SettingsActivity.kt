package com.utn.primerparciallauria.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.utn.primerparciallauria.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settings = PreferenceManager.getDefaultSharedPreferences(this)
        val colorList = settings.getString("colorList","Purple")

        when(colorList){
            "PurpleTheme" -> setTheme(R.style.PurpleTheme)
            "WhiteTheme" -> setTheme(R.style.WhiteTheme)
            "GrayTheme" -> setTheme(R.style.GrayTheme)
        }

        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onStart() {
            super.onStart()
            val settings = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val prevColor = settings.getString("colorList","Purple")

            // Use back press call back to update theme change
            requireActivity().onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        // Custom back pressed
                        if (prevColor != settings.getString("colorList","Purple")){
                            val intent = Intent(activity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent,null)
                            activity?.finish()
                        }
                        else {
                            // Default back pressed
                            setEnabled(false)
                            requireActivity().onBackPressed()
                        }
                    }
                }
            )
        }
    }
}