package com.example.imad5112practicumexam

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Parcelable data class to hold song info
    data class Song(
        val title: String,
        val artist: String,
        val rating: Int,
        val comment: String
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readInt(),
            parcel.readString() ?: ""
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(title)
            parcel.writeString(artist)
            parcel.writeInt(rating)
            parcel.writeString(comment)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<Song> {
            override fun createFromParcel(parcel: Parcel): Song = Song(parcel)
            override fun newArray(size: Int): Array<Song?> = arrayOfNulls(size)
        }
    }

    // List to store songs added by user
    private val SongList = ArrayList<Song>()

    // UI components
    private lateinit var btnAdd: Button
    private lateinit var btnNext: Button
    private lateinit var btnExit: Button
    private lateinit var edtSong: EditText
    private lateinit var edtArtist: EditText
    private lateinit var edtRate: EditText
    private lateinit var edtComments: EditText
    private lateinit var txtSong: TextView
    private lateinit var txtArtist: TextView
    private lateinit var txtRating: TextView
    private lateinit var txtComments: TextView
    private lateinit var txtPrompt: TextView
    private lateinit var txtWelcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Link UI components with layout IDs
        btnAdd = findViewById(R.id.btnAdd)
        btnNext = findViewById(R.id.btnNext)
        btnExit = findViewById(R.id.btnExit)
        edtSong = findViewById(R.id.edtSong)
        edtArtist = findViewById(R.id.edtArtist)
        edtRate = findViewById(R.id.edtRate)
        edtComments = findViewById(R.id.edtComments)
        txtSong = findViewById(R.id.txtSong)
        txtArtist = findViewById(R.id.txtArtist)
        txtRating = findViewById(R.id.txtRating)
        txtComments = findViewById(R.id.txtComments)
        txtPrompt = findViewById(R.id.txtPrompt)
        txtWelcome = findViewById(R.id.txtWelcome)

        // Hide form fields initially
        setFormVisibility(false)

        // Show form when Add button is clicked
        btnAdd.setOnClickListener {
            setFormVisibility(true)
            btnAdd.visibility = View.GONE
            txtWelcome.visibility = View.GONE
        }

        // Validate inputs, add song, and move to Detailed_view
        btnNext.setOnClickListener {
            val title = edtSong.text.toString().trim()
            val artist = edtArtist.text.toString().trim()
            val ratingText = edtRate.text.toString().trim()
            val comment = edtComments.text.toString().trim()

            // Input validation with helpful errors and toasts
            if (title.isEmpty()) {
                edtSong.error = "Enter song title"
                Toast.makeText(this, "Missing song title", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (artist.isEmpty()) {
                edtArtist.error = "Enter artist name"
                Toast.makeText(this, "Missing artist name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (ratingText.isEmpty()) {
                edtRate.error = "Enter a rating"
                Toast.makeText(this, "Missing rating", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val rating = ratingText.toIntOrNull()
            if (rating == null || rating !in 1..5) {
                edtRate.error = "Rating must be 1 to 5"
                Toast.makeText(this, "Invalid rating: 1 to 5 only", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (comment.isEmpty()) {
                edtComments.error = "Enter a comment"
                Toast.makeText(this, "Missing comment", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create Song and add to list
            val song = Song(title, artist, rating, comment)
            SongList.add(song)

            // Clear inputs for next entry
            edtSong.text.clear()
            edtArtist.text.clear()
            edtRate.text.clear()
            edtComments.text.clear()

            Toast.makeText(this, "Song added successfully", Toast.LENGTH_SHORT).show()

            // Start Detailed_view and pass the full song list
            val intent = Intent(this, Detailed_view::class.java)
            intent.putParcelableArrayListExtra("Songs", ArrayList(SongList))
            startActivity(intent)
        }

        // Exit button closes the app
        btnExit.setOnClickListener {
            finish()
        }
    }

    // Helper to show/hide form fields
    private fun setFormVisibility(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        txtComments.visibility = visibility
        txtPrompt.visibility = visibility
        txtArtist.visibility = visibility
        txtRating.visibility = visibility
        txtSong.visibility = visibility
        edtRate.visibility = visibility
        edtArtist.visibility = visibility
        edtComments.visibility = visibility
        edtSong.visibility = visibility
        btnNext.visibility = visibility
    }
}
