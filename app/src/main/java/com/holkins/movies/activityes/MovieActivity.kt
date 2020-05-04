package com.holkins.movies.activityes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.holkins.movies.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie.*
import org.json.JSONException

class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val id = intent.getStringExtra("id")

        var queue = Volley.newRequestQueue(this)
        val url = "http://www.omdbapi.com/?apikey=f6db0489&i=$id"
        val request = JsonObjectRequest(Request.Method.GET,
            url, null, Response.Listener { response ->
                try {
                    val title = response.getString("Title")
                    val year = response.getString("Year")
                    val actors = response.getString("Actors")
                    val plot = response.getString("Plot")
                    val posterURL = response.getString("Poster")

                    nameMovieTextView.text = title
                    plotMovieTextView.text = plot
                    actorsMovieTextView.text = actors
                    yearMovieTextView.text = year

                    Picasso.get()
                        .load(posterURL)
                        .placeholder(R.drawable.placeholder)
                        .fit()
                        .centerInside()
                        .into(posterMovieImageView)


                } catch (e: JSONException) {

                    Log.e("JsonError", e.toString())
                }

            }, Response.ErrorListener { error ->
                Log.e("VolleyError", error.toString())
            })

        queue.add(request)
    }
}
