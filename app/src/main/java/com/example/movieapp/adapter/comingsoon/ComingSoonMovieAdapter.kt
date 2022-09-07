package com.example.movieapp.adapter.comingsoon


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ComingsoonMovieItemBinding
import com.example.movieapp.databinding.PopularMovieItemBinding
import com.example.movieapp.models.movie.MovieResult
import com.example.movieapp.models.tv.TVResults


class ComingSoonMovieAdapter(
    private var onFirstLoad: (movieResult: MovieResult) -> Unit,
): RecyclerView.Adapter<ComingSoonMovieAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<MovieResult>()
    private var liveDataTv = ArrayList<TVResults>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<MovieResult>){
        this.liveData = liveData as ArrayList<MovieResult>
        notifyDataSetChanged()
    }


    class MovieViewHolder(val binding: ComingsoonMovieItemBinding): RecyclerView.ViewHolder(binding.root)


    fun getSelectedItem(position: Int): MovieResult = liveData[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ComingsoonMovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        if(position == 0){
            onFirstLoad(liveData[0])
        }
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].poster_path).into(holder.binding.imgMovie)

    }

    override fun getItemCount(): Int {
        return liveData.size
    }

}