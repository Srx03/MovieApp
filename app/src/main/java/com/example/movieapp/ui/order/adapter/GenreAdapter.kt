package com.example.movieapp.ui.order.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.GenreItemBinding
import com.example.movieapp.models.genres.GenreX
import kotlinx.android.synthetic.main.genre_item.view.*


class GenreAdapter(
     val selectedStrokeColor: Int,
     val unSelectedStrokeColor: Int,
     val cardBgColor: Int,
     val selectGenreItemClick: (genre: GenreX) -> Unit,
     val removeGenreItemClick: (genre: GenreX) -> Unit
): RecyclerView.Adapter<GenreAdapter.MovieViewHolder>() {

    private var liveData: List<GenreX>? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveData: List<GenreX>){
        this.liveData = liveData
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: GenreItemBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(GenreItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.tvGenres.text = liveData!![position].name

        holder.itemView.card.setOnClickListener {
            if(!holder.itemView.card.isChecked){
                holder.itemView.card.isChecked = true
                holder.itemView.card.strokeColor = selectedStrokeColor
                holder.itemView.card.setCardBackgroundColor(cardBgColor)
                selectGenreItemClick(liveData!![position])

            }else{
                holder.itemView.card.isChecked = false
                // Change bg of clicked item
                holder.itemView.card.strokeColor = unSelectedStrokeColor
                // send callback to ui
                removeGenreItemClick(liveData!![position])
            }
        }



    }

    override fun getItemCount(): Int {
        return if(liveData == null) 0
        else  liveData!!.size
    }

}