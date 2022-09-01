package com.example.movieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ActorShowItemBinding
import com.example.movieapp.databinding.RecentMovieItemBinding
import com.example.movieapp.models.MovieCast
import com.example.movieapp.models.MovieResult

class ActorShowAdapter: RecyclerView.Adapter<ActorShowAdapter.MovieViewHolder>() {

    private var liveData = ArrayList<MovieCast>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<MovieCast>){
        this.liveData = liveData as ArrayList<MovieCast>
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: ActorShowItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorShowAdapter.MovieViewHolder {
        return MovieViewHolder(ActorShowItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ActorShowAdapter.MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveData[position].profile_path).into(holder.binding.imgActorShow)
        holder.binding.tvActorRealName.text = liveData[position].name.toString()
        holder.binding.tvActorCharacterName.text = liveData[position].character.toString()

    }

    override fun getItemCount(): Int {
        return liveData.size

    }
}