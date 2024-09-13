package com.example.litreoflightapp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat
import java.text.NumberFormat


class lightcontrol : Fragment() {

    private val ESP8266_IP = "http://192.168.4.1"
    var textView2: TextView? = null
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_lightcontrol, container, false)
        val btnOn = layout.findViewById<Button>(R.id.btnOn)
        val btnOff = layout.findViewById<Button>(R.id.btnOff)
        val textView2 = layout.findViewById<TextView> (R.id.textView2);
        val imgBubl = layout.findViewById<ImageView>(R.id.imvBulb)

        btnOff.setOnClickListener {
            sendHttpRequest("/LED2=OFF")
            imgBubl.setImageResource(R.drawable.bulboff);

            duration = 0
            if(true)
            {
                timer.cancel()
                textView2.text = "00:00:00"
            }

            Toast.makeText(
                requireContext(),
                "Timer turned off!",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnOn.setOnClickListener {
            sendHttpRequest("/LED2=ON")
            imgBubl.setImageResource(R.drawable.bulbon);

            if(duration != 0)
            {
                // Create and start the countdown timer
                timer = object : CountDownTimer(duration * 1000L, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                        // Used for formatting digits to be in 2 digits only
                        val f: NumberFormat = DecimalFormat("00")
                        val hour = millisUntilFinished / 3600000 % 24
                        val min = millisUntilFinished / 60000 % 60
                        val sec = millisUntilFinished / 1000 % 60

                        textView2.setText(
                            (f.format(hour) + ":" + f.format(min)).toString() + ":" + f.format(
                                sec
                            )
                        )

                    }
                    override fun onFinish() {
                        // When the task is over it will print 00:00:00
                        textView2.text = "00:00:00"

                        sendHttpRequest("/LED2=OFF")
                        imgBubl.setImageResource(R.drawable.bulboff);

                        Toast.makeText(
                            requireContext(),
                            "Timer has stopped. Please reset timer.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.start()

                Toast.makeText(
                    requireContext(),
                    "Successfully set light bulb timer!",
                    Toast.LENGTH_SHORT
                ).show()
            }


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