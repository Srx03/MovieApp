package com.example.movieapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.models.Result
import kotlinx.android.synthetic.main.popular_movie_item.view.*

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var liveData: List<Result>? = null
    val test = "test"

    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<Result>){
        this.liveData = liveData
        notifyDataSetChanged()
    }

     class MovieViewHolder(val view: View): RecyclerView.ViewHolder(view){

         val txtTitle = view.findViewById<TextView>(R.id.title)


         fun bind(data: Result){
            txtTitle.text = data.title

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(liveData!!.get(position))
    }

    override fun getItemCount(): Int {
        if (liveData == null){
            return 0
        }
        else {
            return liveData!!.size
        }
    }
}