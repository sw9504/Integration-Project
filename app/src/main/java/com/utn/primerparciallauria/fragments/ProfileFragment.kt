package com.utn.primerparciallauria.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.utn.primerparciallauria.R

class ProfileFragment : Fragment() {
    lateinit var v : View
    lateinit var userWelcome : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profile, container, false)

        userWelcome = v.findViewById(R.id.userWelcome)

        return v
    }

    override fun onStart() {

    }
}