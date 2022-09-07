package com.example.movieapp.adapter.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.TopratedMovieItemBinding
import com.example.movieapp.models.movie.MovieResult

class TopRatedMovieAdapter: RecyclerView.Adapter<TopRatedMovieAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<MovieResult>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<MovieResult>){
        this.liveData = liveData as ArrayList<MovieResult>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: TopratedMovieItemBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(TopratedMovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].poster_path).into(holder.binding.imgMovie)
        holder.binding.tvRating.text = liveData[position].vote_average.toString()

    }

    override fun getItemCount(): Int {
        return liveData.size
    }
}