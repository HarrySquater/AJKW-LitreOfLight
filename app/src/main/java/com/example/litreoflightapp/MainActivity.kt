package com.example.litreoflightapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

var duration = 0

class MainActivity : AppCompatActivity() {

    private val ESP8266_IP = "http://192.168.4.1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initially replace with the Home fragment(see Bottom Navigation Bar - Android Studio, 2022)
        replace(lightcontrol())

        //using findViewById instead of binding
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replace(lightcontrol())
                R.id.settings -> replace(settingsFrag())
                else -> {}
            }
            true
        }
    }

    private fun replace(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.fragment_container,
            fragment
        ) //Replace the fragment in a container(see Bottom Navigation Bar - Android Studio, 2022)
        fragmentTransaction.addToBackStack(null) //Add transaction to back stack for back navigation(see Bottom Navigation Bar - Android Studio, 2022)
        fragmentTransaction.commit() //Commit the transaction(see Bottom Navigation Bar - Android Studio, 2022)
    }
}

