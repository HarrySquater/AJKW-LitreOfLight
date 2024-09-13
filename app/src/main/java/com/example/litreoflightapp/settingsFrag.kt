package com.example.litreoflightapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner


class settingsFrag : Fragment() {

    private lateinit var spinnerTime: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_settings, container, false)
        // Inflate the layout for this fragment
        spinnerTime = layout.findViewById(R.id.spinnerTime)
        val time = arrayOf("30", "60", "90", "120")
        val systemAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, time)
        systemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTime.adapter = systemAdapter

        return layout


    }


}