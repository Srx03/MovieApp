package com.example.movieapp.adapter


import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.models.Result

class RecentMovieAdapter(private val isFirstScreen: Boolean = true): RecyclerView.Adapter<RecentMovieAdapter.MovieViewHolder>() {

    var liveData: List<Result>? = null


    fun setList(liveData: List<Result>){
        this.liveData = liveData
        notifyDataSetChanged()
    }

    class MovieViewHolder(val view: View): RecyclerView.ViewHolder(view){

        val txtTitle = view.findViewById<TextView>(R.id.tvTitle)
        val txtGenre = view.findViewById<TextView>(R.id.tvGenre)
        val imgMovie = view.findViewById<ImageView>(R.id.imgMovie)
        val txtReleaseDate = view.findViewById<TextView>(R.id.tvReleaseDate)
        val txtVoteAverage =  view.findViewById<TextView>(R.id.tvVoteAverege)

        fun bind(data: Result){
            txtTitle.text = data.title
            txtGenre.text = "Daneme, Daneme, Daneme"
            txtReleaseDate.text = data.release_date
            txtVoteAverage.text =  data.vote_average.toString() + " / 10"
            Glide.with(imgMovie)
                .load("https://image.tmdb.org/t/p/w500/" + data.poster_path)
                .into(imgMovie)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentMovieAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentMovieAdapter.MovieViewHolder, position: Int) {
        holder.bind(liveData!!.get(position))

    }

    override fun getItemCount(): Int {
        if (liveData == null){
            return 0
        }
        else if(isFirstScreen){

            return 4

        } else{
            return liveData!!.size
        }
    }
}