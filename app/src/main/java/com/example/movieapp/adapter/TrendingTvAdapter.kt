package com.example.movieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.TrendingTvItemBinding
import com.example.movieapp.models.MovieResult
import com.example.movieapp.models.TVResults


class TrendingTvAdapter: RecyclerView.Adapter<TrendingTvAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<TVResults>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<TVResults>){
        this.liveData = liveData as ArrayList<TVResults>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: TrendingTvItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(TrendingTvItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].poster_path).into(holder.binding.imgMovie)
        holder.binding.tvTitle.text = liveData[position].name

    }

    override fun getItemCount(): Int {
        return liveData.size

    }
}