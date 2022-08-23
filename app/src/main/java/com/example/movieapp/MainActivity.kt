package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.adapter.RecentMovieAdapter
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.models.Movie
import com.example.movieapp.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import retrofit2.Call

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recentMovieAdapter: RecentMovieAdapter
    private lateinit var binding: ActivityMainBinding

    val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       setupRecyclerView()

        viewModel.getObserverLiveData(true).observe(this, object : Observer<Movie> {
            override fun onChanged(t: Movie?) {
                if (t !=null)
                {
                    movieAdapter.setList(t.results)
                    Log.d("Moviespop", t.results.toString())

                }
            }
        })

        viewModel.getObserverLiveData(false).observe(this, object : Observer<Movie> {
            override fun onChanged(t: Movie?) {
                if (t !=null)
                {
                    recentMovieAdapter.setList(t.results)
                    Log.d("Moviesrec", t.results.toString())

                }
            }
        })

        fetchMovies()

    }

    fun setupRecyclerView(){

        movieAdapter = MovieAdapter()
        binding.popularRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = movieAdapter
        }

        recentMovieAdapter = RecentMovieAdapter()
        binding.recentRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.VERTICAL, false)
            adapter = recentMovieAdapter
        }

    }

    fun fetchMovies(){
        CoroutineScope(Dispatchers.IO).launch {

            val job1: Deferred<Unit> = async {
                viewModel.loadData("1", true)
            }
            val job2: Deferred<Unit> = async {
                viewModel.loadData("1", false)
            }

            job1.await()
            job2.await()
        }
    }




}