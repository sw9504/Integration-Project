package com.utn.primerparciallauria.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.utn.primerparciallauria.R
import com.utn.primerparciallauria.database.appDatabase
import com.utn.primerparciallauria.database.userDao
import com.utn.primerparciallauria.entities.User

class CreateAccFragment : Fragment() {

    private var PASSWORD_MIN_LENGTH = 4
    private var PASSWORD_MAX_LENGTH = 8
    private var NAME_MIN_LENGTH = 4
    private var NAME_MAX_LENGTH = 20
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
        inputEmail = v.findViewById(R.id.inputCharacter)
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

            // Validate user input data.
            if (!checkInputLength(name,NAME_MIN_LENGTH,NAME_MAX_LENGTH)){
                Snackbar.make(it,"Name must be between $NAME_MIN_LENGTH-$NAME_MAX_LENGTH character.", Snackbar.LENGTH_SHORT).show()
            }
            else if (!checkInputLength(password,PASSWORD_MIN_LENGTH,PASSWORD_MAX_LENGTH)){
                Snackbar.make(it,"Password must be between $PASSWORD_MIN_LENGTH-$PASSWORD_MAX_LENGTH character.", Snackbar.LENGTH_SHORT).show()
            }
            else if (!checkValidEmail(email)) {
                Snackbar.make(it,"Invalid email.", Snackbar.LENGTH_SHORT).show()
            }
            else if (checkEmailExists(email,userList)){
                Snackbar.make(it,"Email already exists.", Snackbar.LENGTH_SHORT).show()
            }
            else {
                var defaultBio = "Welcome to the House of the Dragon."
                var defaultUrl = "https://cdn-icons-png.flaticon.com/512/929/929493.png?w=360"

                userDao?.addUser(name,email,password,defaultBio,defaultUrl)

                // USe shared preferenced to store logged userId
                var userId = userDao?.getUserId(email) as Int
                val sharedPref : SharedPreferences = requireContext().getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putInt("userId",userId)
                editor.apply()

                // Disable login screen after account created
                var loginPref : SharedPreferences = requireContext().getSharedPreferences("loginPref", Context.MODE_WORLD_WRITEABLE)
                val logEditor = loginPref.edit()
                logEditor.putBoolean("isLogged",true)
                logEditor.apply()

                var action = CreateAccFragmentDirections.actionCreateAccFragmentToListFragment()
                v.findNavController().navigate(action)
            }
        }
    }

    private fun checkInputLength (input : String, minLength : Int, maxLength : Int) : Boolean {
        return input.length in minLength..maxLength
    }

    private fun checkValidEmail(email : String) : Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private fun checkEmailExists(email : String, userEmails : MutableList<User>) : Boolean {
        val exist = userEmails.filter { user ->
            user.email == email
        }
        return exist.isNotEmpty()
    }
}