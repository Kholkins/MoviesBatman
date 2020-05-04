package com.holkins.movies.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.holkins.movies.R
import com.holkins.movies.models.Movie
import com.squareup.picasso.Picasso

class MainAdapter(var items: List<Movie>, val callback: Callback) :
    RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
        private val yearTextView = itemView.findViewById<TextView>(R.id.yearTextView)
        private val posterImageView = itemView.findViewById<ImageView>(R.id.posterImageView)

        fun bind(item: Movie) {

            var url = item.posterURL
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerInside()
                .into(posterImageView)

            titleTextView.text = item.title
            yearTextView.text = item.year

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }

        }
    }

    interface Callback {
        fun onItemClicked(item: Movie)
    }

}