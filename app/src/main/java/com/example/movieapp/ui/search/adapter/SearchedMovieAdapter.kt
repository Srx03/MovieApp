package com.example.movieapp.ui.search.adapter


import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.SearchedMovieItemBinding
import com.example.movieapp.models.movie.MovieResult

class SearchedMovieAdapter: RecyclerView.Adapter<SearchedMovieAdapter.MovieViewHolder>() {

    private var liveData: List<MovieResult>? = null
    var onItemClick: ((MovieResult) -> Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<MovieResult>){
        this.liveData = liveData
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: SearchedMovieItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(SearchedMovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData!![position].poster_path).into(holder.binding.imgMovie)
        holder.binding.tvRating.text = liveData!![position].vote_average.toString()
        holder.binding.tvActorName.text = liveData!![position].title
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(liveData!![position])
        }
    }

    override fun getItemCount(): Int {
        return if(liveData == null) 0
        else  liveData!!.size

    }

    fun setOnPopularMovieItemClick(movie: (MovieResult) -> Unit) {
        onItemClick = movie
    }
}