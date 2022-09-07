package com.example.movieapp.adapter.show

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.SimilarItemBinding
import com.example.movieapp.models.movie.MovieResult
import com.example.movieapp.models.tv.TVResults

class SimilarAdapter(
    private var isMoive: Boolean = true
): RecyclerView.Adapter<SimilarAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<MovieResult>()
    private var liveDataTv = ArrayList<TVResults>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<MovieResult>){
        this.liveData = liveData as ArrayList<MovieResult>
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListTv(liveData: List<TVResults>){
        this.liveDataTv = liveData as ArrayList<TVResults>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: SimilarItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(SimilarItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        if(isMoive){
            Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].poster_path).into(holder.binding.imgMovie)
            holder.binding.tvRating.text =  String.format("%.1f", liveData[position].vote_average)
        }else{
            Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveDataTv[position].poster_path).into(holder.binding.imgMovie)
            holder.binding.tvRating.text =  String.format("%.1f", liveDataTv[position].vote_average)
        }
    }

    override fun getItemCount(): Int {
        return liveData.size

    }
}