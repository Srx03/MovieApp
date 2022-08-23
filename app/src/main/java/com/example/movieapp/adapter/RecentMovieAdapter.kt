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
import com.example.movieapp.databinding.PopularMovieItemBinding
import com.example.movieapp.databinding.RecentMovieItemBinding
import com.example.movieapp.models.Result

class RecentMovieAdapter(): RecyclerView.Adapter<RecentMovieAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<Result>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<Result>){
        this.liveData = liveData as ArrayList<Result>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: RecentMovieItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentMovieAdapter.MovieViewHolder {
        return MovieViewHolder(RecentMovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecentMovieAdapter.MovieViewHolder, position: Int) {
        holder.binding.tvTitle.text = liveData[position].title
        holder.binding.tvGenre.text = "danme, danme, danme"
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].poster_path).into(holder.binding.imgMovie)
        holder.binding.tvReleaseDate.text = liveData[position].release_date
        holder.binding.tvVoteAverege.text = liveData[position].vote_average.toString() + " / 10"

    }

    override fun getItemCount(): Int {
        return liveData.size

    }
}