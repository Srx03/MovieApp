package com.example.movieapp.adapter.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.PopularTvItemBinding
import com.example.movieapp.models.movie.MovieResult
import com.example.movieapp.models.tv.TVResults

class PopularTvAdapter : RecyclerView.Adapter<PopularTvAdapter.MovieViewHolder>() {




    private var liveData = ArrayList<TVResults>()
    var onItemClick: ((TVResults) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<TVResults>){
        this.liveData = liveData as ArrayList<TVResults>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: PopularTvItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(PopularTvItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].poster_path).into(holder.binding.imgMovie)
        holder.binding.tvRating.text = liveData[position].vote_average.toString()
        holder.binding.tvActorName.text = liveData[position].name
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(liveData[position])
        }
    }

    override fun getItemCount(): Int {
        return liveData.size

    }

    fun setOnPopularTvItemClick(tv: (TVResults) -> Unit) {
        onItemClick = tv
    }



}