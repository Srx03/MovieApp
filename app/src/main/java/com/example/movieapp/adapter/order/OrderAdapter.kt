package com.example.movieapp.adapter.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.OrderItemBinding
import com.example.movieapp.models.movie.MovieResult
import com.example.movieapp.models.tv.TVResults

class OrderAdapter(
    private var isMoive: Boolean = true
): RecyclerView.Adapter<OrderAdapter.MovieViewHolder>() {

    private var liveDataMovie: List<MovieResult>? = null
    private var liveDataTv: List<TVResults>? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveDataMovie: List<MovieResult>){
        this.liveDataMovie = liveDataMovie
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListTv(liveDataTv: List<TVResults>){
        this.liveDataTv = liveDataTv
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: OrderItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(OrderItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        if(isMoive){
            Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveDataMovie!![position].poster_path).into(holder.binding.imgMovie)
            holder.binding.tvRating.text =  String.format("%.1f", liveDataMovie!![position].vote_average)
            holder.binding.tvActorName.text = liveDataMovie!![position].title
        }else{
            Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/" + liveDataTv!![position].poster_path).into(holder.binding.imgMovie)
            holder.binding.tvRating.text =  String.format("%.1f", liveDataTv!![position].vote_average)
            holder.binding.tvActorName.text = liveDataTv!![position].name
        }
    }

    override fun getItemCount(): Int {
        if(isMoive){

            return if(liveDataMovie == null) 0
            else  liveDataMovie!!.size

        }else{

            return if(liveDataTv == null) 0
            else  liveDataTv!!.size

        }

    }
}