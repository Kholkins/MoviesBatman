package com.holkins.movies.activityes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.holkins.movies.R
import com.holkins.movies.data.MainAdapter
import com.holkins.movies.models.Movie
import org.json.JSONException
import java.util.ArrayList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var items = ArrayList<Movie>()
        val intent = Intent(this, MovieActivity::class.java)
        val myAdapter = MainAdapter(items, object : MainAdapter.Callback {
            override fun onItemClicked(item: Movie) {

                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        })
        val columnCount = resources.getInteger(R.integer.column_count)
        var layoutManager = GridLayoutManager(this, columnCount)

        var queue = Volley.newRequestQueue(this)
        val url = "http://www.omdbapi.com/?apikey=f6db0489&s=Batman"
        val request = JsonObjectRequest(Request.Method.GET,
            url, null, Response.Listener { response ->
                try {
                    val jsonArray = response.getJSONArray("Search")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val title = jsonObject.getString("Title")
                        val year = jsonObject.getString("Year")
                        val id = jsonObject.getString("imdbID")
                        val posterURL = jsonObject.getString("Poster")

                        items.add(Movie(id, title, posterURL, year))

                    }
                    recyclerView.adapter = myAdapter
                    recyclerView.layoutManager = layoutManager
                    recyclerView.hasFixedSize()
                } catch (e: JSONException) {

                    Log.e("JsonError", e.toString())
                }

            }, Response.ErrorListener { error ->
                Log.e("VolleyError", error.toString()) })

        queue.add(request)
    }
}
