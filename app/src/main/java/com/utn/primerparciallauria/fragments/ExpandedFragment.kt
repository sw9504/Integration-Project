package com.utn.primerparciallauria.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.utn.primerparciallauria.R
import com.utn.primerparciallauria.activities.MainActivity
import com.utn.primerparciallauria.database.appDatabase
import com.utn.primerparciallauria.database.characterDao
import com.utn.primerparciallauria.entities.Character

class ExpandedFragment : Fragment() {
    lateinit var v : View

    lateinit var characterName : TextView
    lateinit var btnEdit : Button
    lateinit var btnDelete : Button
    lateinit var img : ImageView

    private var db: appDatabase? = null
    private var characterDao: characterDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_expanded, container, false)

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar)
        view.visibility = View.GONE

        characterName = v.findViewById(R.id.characterName)
        btnEdit = v.findViewById(R.id.btnEdit)
        btnDelete = v.findViewById(R.id.btnDelete)
        img = v.findViewById(R.id.characterImg)

        return v
    }

    override fun onStart() {
        super.onStart()
        var characterId = ExpandedFragmentArgs.fromBundle(requireArguments()).characterId

        db = appDatabase.getAppDataBase(v.context)
        characterDao = db?.characterDao()

        var character = characterDao?.getCharacter(characterId) as Character

        characterName.text = character.name

        var errorUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYFj79aNLmv5KujpGMVF2_BSbHG2-H_XaCew&usqp=CAU"
        Glide.with(requireContext())
            .load(character.imgAvatar)
            .error(errorUrl)
            .into(img)

        btnEdit.setOnClickListener {
            var action = ExpandedFragmentDirections.actionExpandedFragmentToEditFragment(characterId)
            v.findNavController().navigate(action)
        }

        btnDelete.setOnClickListener {
            // Dialog for Logout confirmation.
            // Use material design library
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete character")
                .setMessage("Are you sure you want to delete ${character.name}?")
                .setNegativeButton("NO") { dialog, which ->
                    // Nothing
                }
                .setPositiveButton("YES") { dialog, which ->
                    characterDao?.deleteCharacter(characterId)
                    v.findNavController().navigateUp()
                }
                .show()
        }
    }
}