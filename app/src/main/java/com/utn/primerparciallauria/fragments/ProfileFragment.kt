package com.utn.primerparciallauria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.utn.primerparciallauria.R

class ProfileFragment : Fragment() {
    lateinit var v : View
    lateinit var btnSettings : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profile, container, false)
        btnSettings = v.findViewById(R.id.btnSettings)
        return v
    }

    override fun onStart() {
        super.onStart()
        btnSettings.setOnClickListener {
            var action = ProfileFragmentDirections.actionProfileFragmentToSettingsActivity()
            v.findNavController().navigate(action)
        }
    }
}