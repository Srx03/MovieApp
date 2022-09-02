package com.example.movieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.data.local.entity.Result
import com.example.movieapp.databinding.TrendingActorItemBinding



class TrendingActorAdapter: RecyclerView.Adapter<TrendingActorAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<Result>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<Result>){
        this.liveData = liveData as ArrayList<Result>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: TrendingActorItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(TrendingActorItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].profile_path).into(holder.binding.imgMovie)
        holder.binding.tvName.text = liveData[position].name

    }

    override fun getItemCount(): Int {
        return liveData.size

    }
}