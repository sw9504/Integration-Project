package com.utn.primerparciallauria.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.utn.primerparciallauria.R
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

        // Load userId through Shared Preferences
        val sharedPref : SharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val userId : Int = sharedPref.getInt("userId",0)

        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()
        val user = userDao?.getUser(userId) as User

        txtUserName.text = user.name
        txtBio.text = user.bio

        Glide.with(requireContext())
            .load(user.imgAvatar)
            .circleCrop()
            .into(imgAvatar)

        btnSettings.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToSettingsActivity()
            v.findNavController().navigate(action)
        }

        btnLogOut.setOnClickListener {

        }
    }
}