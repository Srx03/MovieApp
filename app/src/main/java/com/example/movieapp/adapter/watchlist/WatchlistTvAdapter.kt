package com.example.movieapp.adapter.watchlist

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.data.firebase.movie.WatchList
import com.example.movieapp.data.firebase.tv.TvWatchList
import com.example.movieapp.databinding.WatchlistTvItemBinding
import com.example.movieapp.models.tv.TVResults

class WatchlistTvAdapter : RecyclerView.Adapter<WatchlistTvAdapter.MovieViewHolder>() {




    private var liveData: List<TvWatchList>? = null
    var onItemClick: ((TvWatchList) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<TvWatchList>){
        this.liveData = liveData
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: WatchlistTvItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(WatchlistTvItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData!![position].posterPath).into(holder.binding.imgMovie)
        holder.binding.tvActorName.text = liveData!![position].title
        holder.binding.tvRating.text =  liveData!![position].voteAverage

        holder.itemView.setOnLongClickListener {
            onItemClick!!.invoke (liveData!![position])
            true
        }

    }

    override fun getItemCount(): Int {
        return if(liveData == null) 0
        else  liveData!!.size

    }

    fun deleteWatchlistTvItemClick(tv: (TvWatchList) -> Unit) {
        onItemClick = tv
    }



}