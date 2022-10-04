package com.utn.primerparciallauria.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utn.primerparciallauria.R
import com.utn.primerparciallauria.database.appDatabase
import com.utn.primerparciallauria.database.userDao
import com.utn.primerparciallauria.entities.User

class CreateAccFragment : Fragment() {
    lateinit var v : View
    lateinit var inputName : EditText
    lateinit var inputEmail : EditText
    lateinit var inputPassword : EditText
    lateinit var btnCreate : Button

    private var db: appDatabase? = null
    private var userDao: userDao? = null

    var userList : MutableList<User> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_create_acc, container, false)

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar)
        view.visibility = View.INVISIBLE

        inputName = v.findViewById(R.id.inputName)
        inputEmail = v.findViewById(R.id.inputEmail)
        inputPassword = v.findViewById(R.id.inputPassword)
        btnCreate = v.findViewById(R.id.btnCreate)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()
        userList = userDao?.loadAllUsers() as MutableList<User>


        btnCreate.setOnClickListener {
            var name = inputName.text.toString()
            var email = inputEmail.text.toString()
            var password = inputPassword.text.toString()

            userDao?.addUser(name,email,password,"")
            var userId = userDao?.getUserId(email) as Int
            var action = CreateAccFragmentDirections.actionCreateAccFragmentToListFragment(userId)
            v.findNavController().navigate(action)
        }

    }
}