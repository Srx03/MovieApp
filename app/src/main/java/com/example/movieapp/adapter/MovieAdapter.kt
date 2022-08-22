package com.example.movieapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.PopularMovieItemBinding
import com.example.movieapp.models.Result
import kotlinx.android.synthetic.main.popular_movie_item.view.*

class MovieAdapter(private val isFirstScreen: Boolean = true): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var liveData: List<Result>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<Result>){
        this.liveData = liveData
        notifyDataSetChanged()
    }

    class MovieViewHolder(val view: View): RecyclerView.ViewHolder(view){

        val txtTitle = view.findViewById<TextView>(R.id.tvTitle)
        val txtGenre = view.findViewById<TextView>(R.id.tvGenre)
        val imgMovie = view.findViewById<ImageView>(R.id.imgMovie)

        fun bind(data: Result){
            txtTitle.text = data.title
            txtGenre.text = "asasas"
            Glide.with(imgMovie).load("https://image.tmdb.org/t/p/w500/" + data.poster_path).into(imgMovie)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(liveData!!.get(position))

        Log.d("PopularMovies","asasas")
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