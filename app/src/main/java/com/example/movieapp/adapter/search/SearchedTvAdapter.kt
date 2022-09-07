package com.example.movieapp.adapter.search


import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.SearchedMovieItemBinding
import com.example.movieapp.models.tv.TVResults

class SearchedTvAdapter: RecyclerView.Adapter<SearchedTvAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<TVResults>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<TVResults>){
        this.liveData = liveData as ArrayList<TVResults>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: SearchedMovieItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(SearchedMovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].poster_path).into(holder.binding.imgMovie)
        holder.binding.tvRating.text = liveData[position].vote_average.toString()

    }

    override fun getItemCount(): Int {
        return liveData.size

    }
}