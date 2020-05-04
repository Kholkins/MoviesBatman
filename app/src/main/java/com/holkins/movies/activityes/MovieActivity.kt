package com.holkins.movies.activityes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.holkins.movies.R

class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)

        val id = intent.getStringExtra("id")
    }
}
