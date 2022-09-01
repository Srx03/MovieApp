package com.example.movieapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.RecentMovieItemBinding
import com.example.movieapp.databinding.SimilarItemBinding
import com.example.movieapp.models.MovieResult

class SimilarAdapter: RecyclerView.Adapter<SimilarAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<MovieResult>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<MovieResult>){
        this.liveData = liveData as ArrayList<MovieResult>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: SimilarItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarAdapter.MovieViewHolder {
        return MovieViewHolder(SimilarItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SimilarAdapter.MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].poster_path).into(holder.binding.imgMovie)
        holder.binding.tvRating.text = liveData[position].vote_average.toString()
        Log.d("test", "${liveData[position].vote_average}")

    }

    override fun getItemCount(): Int {
        return liveData.size

    }
}