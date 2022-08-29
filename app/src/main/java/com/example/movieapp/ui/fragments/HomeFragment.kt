package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.adapter.RecentMovieAdapter
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.models.Movie
import com.example.movieapp.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recentMovieAdapter: RecentMovieAdapter
    private val  viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()

        viewModel.getObserverLiveData(true).observe(viewLifecycleOwner, object : Observer<Movie> {
            override fun onChanged(t: Movie?) {
                if (t !=null)
                {
                    movieAdapter.setList(t.results)


                }
            }
        })

        viewModel.getObserverLiveData(false).observe(viewLifecycleOwner, object : Observer<Movie> {
            override fun onChanged(t: Movie?) {
                if (t !=null)
                {
                    recentMovieAdapter.setList(t.results)


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