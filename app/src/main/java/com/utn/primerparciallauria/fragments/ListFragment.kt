package com.utn.primerparciallauria.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.utn.primerparciallauria.R
import com.utn.primerparciallauria.adapters.CharacterAdapter
import com.utn.primerparciallauria.database.appDatabase
import com.utn.primerparciallauria.database.characterDao
import com.utn.primerparciallauria.entities.Character

class ListFragment : Fragment() {
    lateinit var v : View
    lateinit var recCharacter: RecyclerView
    lateinit var adapter: CharacterAdapter
    lateinit var btnAdd : Button

    private var db: appDatabase? = null
    private var characterDao: characterDao? = null

    var characterList : MutableList<Character> = mutableListOf()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            v =  inflater.inflate(R.layout.fragment_list, container, false)

            // Si no pongo esto me desaparece la bottom bar al ir hacia atrás
            val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar)
            view.visibility = View.VISIBLE
            //

            recCharacter = v.findViewById(R.id.recCharacter)
            btnAdd = v.findViewById(R.id.btnAdd)

            return v
        }

        override fun onStart() {
            super.onStart()

            db = appDatabase.getAppDataBase(v.context)
            characterDao = db?.characterDao()

            // Traigo aquellas filas que se correspondan con el userId
            // del usuario que se loguea en LoginFragment
            val sharedPref : SharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val userId : Int = sharedPref.getInt("userId",0)
            characterList = characterDao?.loadAllCharacters(userId) as MutableList<Character>

            // Adapter para recyclerview
            adapter = CharacterAdapter(characterList) { position ->
                var action = ListFragmentDirections.actionListFragmentToExpandedFragment(characterList[position].characterId)
                v.findNavController().navigate(action)
            }
            recCharacter.layoutManager = LinearLayoutManager(requireContext())
            recCharacter.adapter = adapter
            //////////////////////////////

            // Button to add characters
            btnAdd.setOnClickListener {
                var action = ListFragmentDirections.actionListFragmentToNewCharacterFragment(userId)
                v.findNavController().navigate(action)
            }

            // Back press callback to exit app on List
            requireActivity().onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        // Dialog for exit confirmation.
                        // Use material design library
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Exit HOTD App")
                            .setMessage("Are you sure you want to quit?")
                            .setNegativeButton("NO") { dialog, which ->
                                // Nothing
                            }
                            .setPositiveButton("YES") { dialog, which ->
                                activity?.finish()
                            }
                            .show()
                    }
                }
            )
        }
}
