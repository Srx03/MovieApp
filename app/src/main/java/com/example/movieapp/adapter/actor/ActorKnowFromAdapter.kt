package com.example.movieapp.adapter.actor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ActorKnowFromMovieItemBinding
import com.example.movieapp.databinding.ActorShowItemBinding
import com.example.movieapp.models.actor.ActorCredits
import com.example.movieapp.models.actor.Result
import com.example.movieapp.models.movie.MovieCast
import com.example.movieapp.models.movie.MovieResult
import com.example.movieapp.models.tv.TvCast
import com.example.movieapp.util.Constants.imgActor

class ActorKnowFromAdapter: RecyclerView.Adapter<ActorKnowFromAdapter.MovieViewHolder>() {

    private var liveDataActor = ArrayList<MovieResult>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveDataActor: List<MovieResult>){
        this.liveDataActor = liveDataActor as ArrayList<MovieResult>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: ActorKnowFromMovieItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ActorKnowFromMovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

            Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/w500/" + liveDataActor[position].poster_path)
                .into(holder.binding.imgMovie)
                holder.binding.tvActorName.text = liveDataActor[position].title
                holder.binding.tvRating.text = String.format("%.1f", liveDataActor[position].vote_average)

    }

    override fun getItemCount(): Int {
            return liveDataActor.size


    }





}