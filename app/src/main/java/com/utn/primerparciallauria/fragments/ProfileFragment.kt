package com.utn.primerparciallauria.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.utn.primerparciallauria.R
import com.utn.primerparciallauria.activities.MainActivity
import com.utn.primerparciallauria.database.appDatabase
import com.utn.primerparciallauria.database.userDao
import com.utn.primerparciallauria.entities.User

class ProfileFragment : Fragment() {
    lateinit var v : View
    lateinit var txtUserName : TextView
    lateinit var imgAvatar : ImageView
    lateinit var txtBio : TextView
    lateinit var btnSettings : Button
    lateinit var btnLogOut : Button


    private var db: appDatabase? = null
    private var userDao: userDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profile, container, false)

        txtUserName = v.findViewById(R.id.txtUserName)
        txtBio= v.findViewById(R.id.txtBio)
        imgAvatar = v.findViewById(R.id.imgAvatar)
        btnSettings = v.findViewById(R.id.btnSettings)
        btnLogOut = v.findViewById(R.id.btnLogOut)

        return v
    }

    override fun onStart() {
        super.onStart()

        // Setting apply
        val settings = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val circleAvatar = settings.getBoolean("circleAvatar",true)

        // Load userId through Shared Preferences
        val sharedPref : SharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val userId : Int = sharedPref.getInt("userId",0)

        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()
        val user = userDao?.getUser(userId) as User

        txtUserName.text = user.name
        txtBio.text = user.bio

        var errorUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYFj79aNLmv5KujpGMVF2_BSbHG2-H_XaCew&usqp=CAU"

        if (circleAvatar)
            Glide.with(requireContext()).load(user.imgAvatar).circleCrop().error(errorUrl).into(imgAvatar)
        else
            Glide.with(requireContext()).load(user.imgAvatar).error(errorUrl).into(imgAvatar)

        imgAvatar.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToAvatarFragment(userId)
            v.findNavController().navigate(action)
        }

        btnSettings.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToSettingsActivity()
            v.findNavController().navigate(action)
        }

        btnLogOut.setOnClickListener {
            // Dialog for Logout confirmation.
            // Use material design library
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to Logout?")
                .setNegativeButton("NO") { dialog, which ->
                    // Nothing
                }
                .setPositiveButton("YES") { dialog, which ->
                    var loginPref : SharedPreferences = requireContext().getSharedPreferences("loginPref",Context.MODE_PRIVATE)
                    val editor = loginPref.edit()
                    editor.putBoolean("isLogged",false)
                    editor.apply()

                    // Go to LoginFragment with no back stack
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent,null)
                    activity?.finish()
                }
                .show()
        }
    }
}