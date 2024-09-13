package com.example.litreoflightapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.setFragmentResult


class settingsFrag : Fragment() {

    private lateinit var spinnerTime: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_settings, container, false)

        // Inflate the layout for this fragment
        spinnerTime = layout.findViewById(R.id.spinnerTime)
        val time = arrayOf("Set Timer", "30 minutes", "1 hour", "1 hour 30 minutes", "2 hours")

        val systemAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, time)
        systemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTime.adapter = systemAdapter

        val btnTimer = layout.findViewById<Button>(R.id.btnSetTime)

        btnTimer.setOnClickListener {
            val selectedTime = spinnerTime.selectedItem.toString()

            if(selectedTime==("Set Timer"))
            {
                duration = 0
            }
            if(selectedTime==("30 minutes"))
            {
                duration = 1800
            }
            if(selectedTime==("1 hour"))
            {
                duration = 3600
            }
            if(selectedTime==("1 hour 30 minutes"))
            {
                duration = 5400
            }
            if(selectedTime==("2 hours"))
            {
                duration = 7200
            }

            Toast.makeText(
                requireContext(),
                "Timer is set. Turn light bulb on.",
                Toast.LENGTH_SHORT
            ).show()
        }
        return layout

    }
}