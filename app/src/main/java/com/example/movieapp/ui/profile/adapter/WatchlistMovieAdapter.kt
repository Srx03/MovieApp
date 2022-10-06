package com.example.movieapp.ui.profile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.data.firebase.movie.WatchList
import com.example.movieapp.databinding.WatchlistMovieItemBinding

class WatchlistMovieAdapter: RecyclerView.Adapter<WatchlistMovieAdapter.MovieViewHolder>() {


    private var liveData: List<WatchList>? = null
    var onItemClick: ((WatchList) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<WatchList>){
        this.liveData = liveData
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: WatchlistMovieItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(WatchlistMovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData!![position].posterPath).into(holder.binding.imgMovie)
        holder.binding.tvActorName.text = liveData!![position].title
        holder.binding.tvRating.text =  liveData!![position].voteAverage
        holder.itemView.setOnLongClickListener {
            onItemClick!!.invoke(liveData!![position])
            true

        }
    }

    override fun getItemCount(): Int {
        return if(liveData == null) 0
        else  liveData!!.size

    }

    fun deleteWatchlistMovieItemClick(movie: (WatchList) -> Unit) {
        onItemClick = movie
    }



}