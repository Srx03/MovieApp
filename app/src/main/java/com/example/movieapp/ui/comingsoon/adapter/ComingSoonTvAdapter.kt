package com.example.movieapp.ui.comingsoon.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ComingsoonMovieItemBinding
import com.example.movieapp.models.tv.TVResults


class ComingSoonTvAdapter(
    private var onFirstLoad: (tvResult: TVResults) -> Unit
): RecyclerView.Adapter<ComingSoonTvAdapter.MovieViewHolder>() {

    private var liveData: List<TVResults>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<TVResults>){
        this.liveData = liveData
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: ComingsoonMovieItemBinding): RecyclerView.ViewHolder(binding.root)


    fun getSelectedItem(position: Int): TVResults = liveData!![position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ComingsoonMovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        if(position == 0){
            onFirstLoad(liveData!![0])
        }
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData!![position].poster_path).into(holder.binding.imgMovie)

    }

    override fun getItemCount(): Int {
        return if(liveData == null) 0
        else  liveData!!.size

    }

}