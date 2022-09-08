package com.example.movieapp.ui.activitis

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.adapter.show.ActorShowAdapter
import com.example.movieapp.adapter.show.SimilarAdapter
import com.example.movieapp.databinding.ShowActivityBinding
import com.example.movieapp.ui.viewmodel.ShowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowActivity: AppCompatActivity() {

    private lateinit var binding: ShowActivityBinding
    private lateinit var actorShowAdapter: ActorShowAdapter
    private lateinit var similarAdapter: SimilarAdapter
    private val  viewModel: ShowViewModel by viewModels()


    private lateinit var id: String
    private lateinit var idTv: String
    private lateinit var isMovie: String
    private var isClicked: Boolean = false
    private var isMovieHelp: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShowActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getOnPopularClickData()


        if (isMovie == "0"){
            viewModel.movieCreditsList.observe(this,{
                actorShowAdapter.setList(it.cast)
            })

            viewModel.similarMovieList.observe(this,{
                similarAdapter.setList(it.results)
            })

            viewModel.movieDetailList.observe(this,{
                Glide.with(applicationContext)
                    .load("https://image.tmdb.org/t/p/w500/" + it.poster_path)
                    .into(binding.youtubePlayerView)
                binding.tvTitle.text = it.title
                binding.tvYear.text = it.release_date
                binding.tvOverview.text = it.overview
                binding.tvRating.text = String.format("%.1f", it.vote_average)
                binding.tvGenres.text = it.genres.joinToString ("  /  "){ it.name }
            })
        }else{
            viewModel.tvCreditsList.observe(this,{
                actorShowAdapter.setListTv(it.cast)
            })

            viewModel.similarTvList.observe(this,{
                similarAdapter.setListTv(it.results)
            })
            viewModel.tvDetailList.observe(this,{
                Glide.with(applicationContext)
                    .load("https://image.tmdb.org/t/p/w500/" + it.poster_path)
                    .into(binding.youtubePlayerView)
                binding.tvTitle.text = it.name
                binding.tvYear.text = it.first_air_date
                binding.tvOverview.text = it.overview
                binding.tvRating.text = String.format("%.1f", it.vote_average)
                binding.tvGenres.text = it.genres.joinToString ("  /  "){ it.name }
            })
        }


        setUpRecyclerView()
        setUpClickListeners()

    }


    private fun getOnPopularClickData() {
        val intent = intent
        id = intent.getStringExtra("id").toString()
        idTv = intent.getStringExtra("idTv").toString()
        isMovie = intent.getStringExtra("isMovie").toString()

        if(isMovie == "0"){
            isMovieHelp = true
            viewModel.getSimilarMovie(id)
            viewModel.getMovieCredits(id)
            viewModel.getMoiveDetail(id)
        }
        else {
            isMovieHelp = false
            viewModel.getTvDetail(idTv)
            viewModel.getTvCredits(idTv)
            viewModel.getSimilarTv(idTv)
        }


    }




    private fun setUpRecyclerView(){
        actorShowAdapter = ActorShowAdapter(isMovie)
        similarAdapter = SimilarAdapter(isMovie)

        binding.actorsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = actorShowAdapter
        }

        binding.similarRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = similarAdapter
        }


    }

    private fun setUpClickListeners() = binding.apply {

        tvOverview.setOnClickListener {
            if (!isClicked) {
                // Expand it
                isClicked = true
                tvOverview.maxLines = 100
            } else {
                // Collapse it
                isClicked = false
                tvOverview.maxLines = 4
            }
        }
    }


}