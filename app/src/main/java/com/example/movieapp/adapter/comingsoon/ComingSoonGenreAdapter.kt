package com.example.movieapp.adapter.comingsoon


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ComingsoonMovieItemBinding
import com.example.movieapp.databinding.GenreItemBinding
import com.example.movieapp.databinding.PopularMovieItemBinding
import com.example.movieapp.models.genres.GenreX
import com.example.movieapp.models.movie.MovieResult


class ComingSoonGenreAdapter(): RecyclerView.Adapter<ComingSoonGenreAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<GenreX>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<GenreX>){
        this.liveData = liveData as ArrayList<GenreX>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: GenreItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(GenreItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.tvGenres.text = liveData[position].name
    }

    override fun getItemCount(): Int {
        return liveData.size
    }

}