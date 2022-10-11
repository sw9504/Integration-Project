package com.utn.primerparciallauria.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.utn.primerparciallauria.R
import com.utn.primerparciallauria.database.appDatabase
import com.utn.primerparciallauria.database.characterDao

class NewCharacterFragment : Fragment() {
    lateinit var v : View

    private var NAME_MIN_LENGTH = 4
    private var NAME_MAX_LENGTH = 25

    lateinit var inputName : EditText
    lateinit var inputUrl : EditText
    lateinit var btnAdd : Button

    private var db: appDatabase? = null
    private var characterDao: characterDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_new_character, container, false)

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar)
        view.visibility = View.GONE

        inputName = v.findViewById(R.id.inputCharacter)
        inputUrl = v.findViewById(R.id.inputUrl)
        btnAdd = v.findViewById(R.id.btnRecovery)

        return v
    }

    override fun onStart() {
        super.onStart()
        db = appDatabase.getAppDataBase(v.context)
        characterDao = db?.characterDao()

        var userId = NewCharacterFragmentArgs.fromBundle(requireArguments()).userId

        btnAdd.setOnClickListener {
            val name = inputName.text.toString()
            val url = inputUrl.text.toString()

            if (!checkInputLength(name,NAME_MIN_LENGTH,NAME_MAX_LENGTH)) {
                Snackbar.make(it,"Name must be between $NAME_MIN_LENGTH-$NAME_MAX_LENGTH character.", Snackbar.LENGTH_SHORT).show()
            }
            else if (!checkImageUrl(url)) {
                Snackbar.make(it,"Invalid image url.", Snackbar.LENGTH_SHORT).show()
            }
            else {
                characterDao?.addCharacter(userId, name,url)
                v.findNavController().navigateUp()
            }
        }

    }

    private fun checkInputLength (input : String, minLength : Int, maxLength : Int) : Boolean {
        return input.length in minLength..maxLength
    }

    private fun checkImageUrl (url : String) : Boolean {
        if (url != "" && url != null) {
            // Some code to check if url is a img file
            return true
        }
        return false
    }
}