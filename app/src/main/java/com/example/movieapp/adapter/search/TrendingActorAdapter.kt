package com.example.movieapp.adapter.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.models.actor.Result
import com.example.movieapp.databinding.TrendingActorItemBinding
import com.example.movieapp.models.movie.MovieResult
import com.example.movieapp.util.Constants.imgActor


class TrendingActorAdapter: RecyclerView.Adapter<TrendingActorAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<Result>()
    var onItemClick: ((Result) -> Unit)? = null


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
        Glide.with(holder.itemView).load(imgActor + liveData[position].profile_path).into(holder.binding.imgMovie)
        holder.binding.tvName.text = liveData[position].name

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(liveData[position])
        }
    }

    override fun getItemCount(): Int {
        return liveData.size

    }

    fun setOnPopularMovieItemClick(actor: (Result) -> Unit) {
        onItemClick = actor
    }
}