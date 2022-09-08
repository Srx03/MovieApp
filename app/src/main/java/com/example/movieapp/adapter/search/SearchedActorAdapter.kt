package com.example.movieapp.adapter.search


import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.models.actor.Result
import com.example.movieapp.databinding.SearchedActorItemBinding
import com.example.movieapp.util.Constants.imgActor

class SearchedActorAdapter: RecyclerView.Adapter<SearchedActorAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<Result>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<Result>){
        this.liveData = liveData as ArrayList<Result>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: SearchedActorItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(SearchedActorItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load(
            imgActor +
                liveData[position].profile_path).into(holder.binding.imgMovie)
        holder.binding.tvActorName.text = liveData[position].name
    }

    override fun getItemCount(): Int {
        return liveData.size

    }
}