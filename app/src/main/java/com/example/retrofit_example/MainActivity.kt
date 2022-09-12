package com.example.retrofit_example

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.*


class MainActivity : AppCompatActivity() {

    private lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)

        //api Call
        apiCall()

        //refresh loading button
        val refreshButton = findViewById<ImageButton>(R.id.refresh)

        refreshButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            apiCall()
        }

    }

    private fun apiCall() {
        val apiInterface: ApiInterface? = Constants.getClient()?.create(ApiInterface::class.java)
        apiInterface?.getAnimeList()?.enqueue(object : Callback<AnimeResponse> {
            override fun onResponse(call: Call<AnimeResponse>, response: Response<AnimeResponse>) {
                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE
                    setData(response.body()?.quote, response.body()?.character,response.body()?.anime)
                }
            }

            override fun onFailure(call: Call<AnimeResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    //set Data in specified textviews
    private fun setData(quote: String?, character: String?, anime: String?) {
        val textQuote = findViewById<TextView>(R.id.text_quote)
        textQuote.setText(quote)

        val textCharactor = findViewById<TextView>(R.id.text_character)
        textCharactor.setText("-$character")

        val textAnime = findViewById<TextView>(R.id.text_anime)
        textAnime.setText("Anime- $anime")
    }


}