package com.example.movieapp.adapter


import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.RecentMovieItemBinding
import com.example.movieapp.models.MovieResult

class RecentMovieAdapter: RecyclerView.Adapter<RecentMovieAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<MovieResult>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<MovieResult>){
        this.liveData = liveData as ArrayList<MovieResult>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: RecentMovieItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentMovieAdapter.MovieViewHolder {
        return MovieViewHolder(RecentMovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecentMovieAdapter.MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].poster_path).into(holder.binding.imgMovie)
        holder.binding.tvRating.text = liveData[position].vote_average.toString()

    }

    override fun getItemCount(): Int {
        return liveData.size

    }
}