package com.utn.primerparciallauria.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.utn.primerparciallauria.R
import com.utn.primerparciallauria.database.appDatabase
import com.utn.primerparciallauria.database.userDao
import com.utn.primerparciallauria.entities.Character
import com.utn.primerparciallauria.entities.User

class LoginFragment : Fragment() {

    lateinit var v : View
    lateinit var txtUser : EditText
    lateinit var txtPass : EditText
    lateinit var btnLogin : Button

    lateinit var txtRecovery : TextView
    lateinit var txtCreateAcc : TextView

    private var db: appDatabase? = null
    private var userDao: userDao? = null

    private var userList : MutableList<User> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar)
        view.visibility = View.INVISIBLE

        btnLogin = v.findViewById(R.id.btnLogin)
        txtUser = v.findViewById(R.id.txtUser)
        txtPass = v.findViewById(R.id.txtPass)

        txtRecovery = v.findViewById(R.id.txtRecovery)
        txtCreateAcc = v.findViewById(R.id.txtCreateAcc)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()

        userList = userDao?.loadAllUsers() as MutableList<User>

        btnLogin.setOnClickListener {
            var email = txtUser.text.toString()
            var pass = txtPass.text.toString()

            var newList = userList.filter { us ->
                us.email == email && us.password == pass
            }

            if (newList.isEmpty())
                Snackbar.make(it,"Datos incorrectos", Snackbar.LENGTH_SHORT).show()
            else {
                Snackbar.make(it,"Autenticado", Snackbar.LENGTH_SHORT).show()

                var userId = userDao?.getUserId(email) as Int

                // Using popUpto can't apply arguments on ListFragment.kt
                //val action = LoginFragmentDirections.actionLoginFragmentToListFragment(userId)

                val sharedPref : SharedPreferences = requireContext().getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putInt("userId",userId)
                editor.apply()

                val action = LoginFragmentDirections.actionLoginFragmentToListFragment()
                v.findNavController().navigate(action)
            }
        }

        txtRecovery.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRecoveryFragment()
            v.findNavController().navigate(action)
        }

        txtCreateAcc.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToCreateAccFragment()
            v.findNavController().navigate(action)
        }
    }
}