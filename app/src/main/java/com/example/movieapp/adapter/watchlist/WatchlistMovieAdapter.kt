package com.example.movieapp.adapter.watchlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.WatchlistTvItemBinding
import com.example.movieapp.models.tv.TVResults

class WatchlistMovieAdapter : RecyclerView.Adapter<WatchlistMovieAdapter.MovieViewHolder>() {




    private var liveData: List<TVResults>? = null
    var onItemClick: ((TVResults) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<TVResults>){
        this.liveData = liveData
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: WatchlistTvItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(WatchlistTvItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData!![position].poster_path).into(holder.binding.imgMovie)
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(liveData!![position])
        }
    }

    override fun getItemCount(): Int {
        return if(liveData == null) 0
        else  liveData!!.size

    }

    fun setOnPopularTvItemClick(tv: (TVResults) -> Unit) {
        onItemClick = tv
    }



}