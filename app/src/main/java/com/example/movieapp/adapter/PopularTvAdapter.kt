package com.example.movieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.PopularTvItemBinding
import com.example.movieapp.models.Movie
import com.example.movieapp.models.TVResults

class PopularTvAdapter : RecyclerView.Adapter<PopularTvAdapter.MovieViewHolder>() {




    private var liveData = ArrayList<TVResults>()
    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<TVResults>){
        this.liveData = liveData as ArrayList<TVResults>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: PopularTvItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTvAdapter.MovieViewHolder {
        return MovieViewHolder(PopularTvItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PopularTvAdapter.MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].poster_path).into(holder.binding.imgMovie)
        holder.binding.tvRating.text = liveData[position].vote_average.toString()

    }

    override fun getItemCount(): Int {
        return liveData.size

    }



}