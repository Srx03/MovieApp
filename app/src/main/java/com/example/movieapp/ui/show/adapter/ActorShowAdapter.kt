package com.example.movieapp.ui.show.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ActorShowItemBinding
import com.example.movieapp.models.movie.MovieCast
import com.example.movieapp.models.tv.TvCast
import com.example.movieapp.util.Constants.imgActor

class ActorShowAdapter(
    private var isMoive: String = "1"
): RecyclerView.Adapter<ActorShowAdapter.MovieViewHolder>() {

    private var liveDataMovie: List<MovieCast>? = null
    private var liveDataTv: List<TvCast>? = null

    var onItemClickMovieActor: ((MovieCast) -> Unit)? = null
    var onItemClickTvActor: ((TvCast) -> Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveDataMovie: List<MovieCast>){
        this.liveDataMovie = liveDataMovie
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setListTv(liveDataTv: List<TvCast>){
        this.liveDataTv = liveDataTv
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: ActorShowItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ActorShowItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        if (isMoive == "0") {

            Glide.with(holder.itemView)
                .load(imgActor + liveDataMovie!![position].profile_path)
                .into(holder.binding.imgActorShow)
            holder.binding.tvActorRealName.text = liveDataMovie!![position].name.toString()
            holder.binding.tvActorCharacterName.text = liveDataMovie!![position].character.toString()
            holder.itemView.setOnClickListener {
                onItemClickMovieActor!!.invoke(liveDataMovie!![position])
            }
        }else{
            Glide.with(holder.itemView)
                .load(imgActor + liveDataTv!![position].profile_path)
                .into(holder.binding.imgActorShow)
            holder.binding.tvActorRealName.text = liveDataTv!![position].name
            holder.binding.tvActorCharacterName.text = liveDataTv!![position].character
            holder.itemView.setOnClickListener {
                onItemClickTvActor!!.invoke(liveDataTv!![position])
            }
        }
    }

    override fun getItemCount(): Int {
        if(isMoive == "0" ){

            return if(liveDataMovie == null) 0
            else  liveDataMovie!!.size

        }else{

            return if(liveDataTv == null) 0
            else  liveDataTv!!.size

        }

    }

    fun setOnMovieActorItemClick(actorMovie: (MovieCast) -> Unit) {
        onItemClickMovieActor = actorMovie
    }

    fun setOnTvActorItemClick(actorTv: (TvCast) -> Unit) {
        onItemClickTvActor = actorTv
    }

}