package com.example.litreoflightapp

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class lightcontrol : Fragment() {

    private val ESP8266_IP = "http://192.168.4.1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_lightcontrol, container, false)
        val btnOn = layout.findViewById<Button>(R.id.btnOn)
        val btnOff = layout.findViewById<Button>(R.id.btnOff)

        val imgBubl = layout.findViewById<ImageView>(R.id.imvBulb)

        btnOff.setOnClickListener {
            sendHttpRequest("/LED2=OFF")
            imgBubl.setImageResource(R.drawable.bulboff);

        }

        btnOn.setOnClickListener {
            sendHttpRequest("/LED2=ON")
            imgBubl.setImageResource(R.drawable.bulbon);
//
//            Handler().postDelayed({
//                sendHttpRequest("/LED=ON")
//                imgBubl.setImageResource(R.drawable.bulboff);
//            }, SPLASH_DURATION)
        }
        return layout
    }

    private fun sendHttpRequest(command: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("$ESP8266_IP$command")
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                val responseCode = urlConnection.responseCode
                if (responseCode == 200) {
                    showToast("Success: $command")
                } else {
                    showToast("Failed: $command")
                }
                urlConnection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
                showToast("Error: ${e.message}")
            }
        }
    }

    private fun showToast(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

}