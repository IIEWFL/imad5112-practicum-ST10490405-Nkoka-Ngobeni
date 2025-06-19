package com.example.imad5112practicumexam

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imad5112practicumexam.MainActivity.Song

class Detailed_view : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)

        val songList = intent.getParcelableArrayListExtra<MainActivity.Song>("Songs") ?: arrayListOf()
        Toast.makeText(this, "Songs received: ${songList.size}", Toast.LENGTH_SHORT).show()

        val btnShowSongs: Button = findViewById(R.id.btnShowSongs)
        val btnAverage: Button = findViewById(R.id.btnAverage)
        val txtDisplay: TextView = findViewById(R.id.txtDisplay)
        val btnAddMore: Button = findViewById(R.id.btnAddMore)

        // Get the list of songs from Intent (Parcelable)


        btnShowSongs.setOnClickListener {
            val output = StringBuilder()
            for ((index, song) in songList.withIndex()) {
                output.append("Song ${index + 1}\n")
                output.append("Title: ${song.title}\n")
                output.append("Artist: ${song.artist}\n")
                output.append("Rating: ${song.rating}\n")
                output.append("Comment: ${song.comment}\n\n")
            }
            txtDisplay.text = output.toString()
        }

        btnAverage.setOnClickListener {
            if (songList.isEmpty()) {
                txtDisplay.text = "No songs added yet."
                return@setOnClickListener
            }
            var total = 0
            for (song in songList) {
                total += song.rating
            }
            val average = total.toDouble() / songList.size
            txtDisplay.text = "Average Rating: %.2f".format(average)
        }





        btnAddMore.setOnClickListener {
            // Launch MainActivity to add more songs
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() //
        }

    }
    }


