package com.truth_or_dare

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var greencard: TextView
    lateinit var bluecard: TextView
    lateinit var myImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        greencard = findViewById(R.id.green_text)
        bluecard = findViewById(R.id.blue_text)
        //myImageView = findViewById<ImageView>(R.id.exitbtn)!!


        greencard.setOnClickListener {
            var intent = Intent(this, TowPlayerPage::class.java)
            startActivity(intent)
        }
        bluecard.setOnClickListener {
            var intent = Intent(this, ChoosePlayers::class.java)
            startActivity(intent)
        }


    }
}