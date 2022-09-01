package com.example.movieapp.ui.activitis

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.adapter.ActorShowAdapter
import com.example.movieapp.adapter.TopRatedTvAdapter
import com.example.movieapp.databinding.ShowActivityBinding
import com.example.movieapp.ui.viewmodel.HomeViewModel
import com.example.movieapp.ui.viewmodel.ShowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowActivity: AppCompatActivity() {

    private lateinit var binding: ShowActivityBinding
    private lateinit var actorShowAdapter: ActorShowAdapter
    private val  viewModel: ShowViewModel by viewModels()

    private lateinit var title: String
    private lateinit var overview: String
    private lateinit var date: String
    private lateinit var image: String
    private lateinit var vote: String
    private lateinit var id: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShowActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getOnPopularMovieClickData()
        setUpInformations()

        viewModel.getMovieCredits(id)
        Log.d("PopularShowId", "$id")

        viewModel.movieCreditsList.observe(this,{
            actorShowAdapter.setList(it.cast)
        })

        setUpRecyclerView()

    }


    private fun getOnPopularMovieClickData() {
        val intent = intent
       title = intent.getStringExtra("title").toString()
        date = intent.getStringExtra("date").toString()
        overview = intent.getStringExtra("overview").toString()
        image = intent.getStringExtra("image").toString()
        vote = intent.getStringExtra("vote").toString()
        id = intent.getStringExtra("id").toString()
        Log.d("PopularShow", "$vote")

    }

    private fun setUpInformations(){
        Glide.with(applicationContext)
            .load("https://image.tmdb.org/t/p/w500/" + image)
            .into(binding.youtubePlayerView)
        binding.tvTitle.text = title
        binding.tvYear.text = date
        binding.tvOverview.text = overview
        binding.tvRating.text = vote

    }

    private fun setUpRecyclerView(){
        actorShowAdapter = ActorShowAdapter()


        binding.actorsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = actorShowAdapter
        }

    }


}