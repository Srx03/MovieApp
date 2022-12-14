package com.example.movieapp.ui.comingsoon.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.GenreItemBinding
import com.example.movieapp.models.genres.GenreX


class ComingSoonGenreAdapter(): RecyclerView.Adapter<ComingSoonGenreAdapter.MovieViewHolder>() {

    private var liveData: List<GenreX>? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<GenreX>){
        this.liveData = liveData
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: GenreItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(GenreItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.tvGenres.text = liveData!![position].name
    }

    override fun getItemCount(): Int {
        return if(liveData == null) 0
        else  liveData!!.size
    }

}