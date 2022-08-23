package com.example.movieapp.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.PopularMovieItemBinding
import com.example.movieapp.models.Result


class MovieAdapter(): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<Result>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<Result>){
        this.liveData = liveData as ArrayList<Result>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: PopularMovieItemBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(PopularMovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.tvTitle.text = liveData[position].title
        holder.binding.tvGenre.text = "danme, danme, dasasasasasase"
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].poster_path).into(holder.binding.imgMovie)

    }

    override fun getItemCount(): Int {
        return liveData.size
    }
}