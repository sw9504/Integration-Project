package com.utn.primerparciallauria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.utn.primerparciallauria.R
import com.utn.primerparciallauria.database.appDatabase
import com.utn.primerparciallauria.database.characterDao
import com.utn.primerparciallauria.database.userDao
import com.utn.primerparciallauria.entities.Character
import com.utn.primerparciallauria.entities.User

class AvatarFragment : Fragment() {
    lateinit var v : View
    lateinit var inputBio : EditText
    lateinit var inputUrl : EditText
    lateinit var btnUpdate : Button

    private var db: appDatabase? = null
    private var userDao: userDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_avatar, container, false)

        inputBio = v.findViewById(R.id.inputBio)
        inputUrl = v.findViewById(R.id.inputUrl)
        btnUpdate = v.findViewById(R.id.btnUpdate)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()

        var userId = AvatarFragmentArgs.fromBundle(requireArguments()).userId
        var user = userDao?.getUser(userId) as User

        inputBio.setText(user.bio)
        inputUrl.setText(user.imgAvatar)

        btnUpdate.setOnClickListener {
            val bio = inputBio.text.toString()
            val url = inputUrl.text.toString()
            userDao?.updateUserProfile(userId,bio,url)
            v.findNavController().navigateUp()
        }
    }

}