package com.utn.primerparciallauria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utn.primerparciallauria.R
import com.utn.primerparciallauria.database.appDatabase
import com.utn.primerparciallauria.database.characterDao
import com.utn.primerparciallauria.database.userDao
import com.utn.primerparciallauria.entities.User

lateinit var v : View
lateinit var inputEmail : EditText
lateinit var btnRecovery : Button

private var db: appDatabase? = null
private var userDao: userDao? = null

class RecoveryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_recovery, container, false)

        inputEmail = v.findViewById(R.id.inputEmail)
        btnRecovery = v.findViewById(R.id.btnRecovery)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()

        var userList : MutableList<User> = mutableListOf()
        userList = userDao?.loadAllUsers() as MutableList<User>

        btnRecovery.setOnClickListener {
            var email = inputEmail.text.toString()

            var newList = userList.filter { us ->
                us.email == email
            }

            if (newList.isEmpty())
                Snackbar.make(it,"Incorrect email.", Snackbar.LENGTH_SHORT).show()
            else {
                var userPass = userDao?.getUserPass(email) as String
                Snackbar.make(it,"Your password is $userPass", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}