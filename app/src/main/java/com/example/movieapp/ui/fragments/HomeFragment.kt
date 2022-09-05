package com.example.movieapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.adapter.home.*
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.ui.activitis.ShowActivity
import com.example.movieapp.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var popularTvAdapter: PopularTvAdapter
    private lateinit var recentMovieAdapter: RecentMovieAdapter
    private lateinit var topRatedMovieAdapter: TopRatedMovieAdapter
    private lateinit var topRatedTvAdapter: TopRatedTvAdapter
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

        viewModel.popularMovieList.observe(viewLifecycleOwner, {
            movieAdapter.setList(it.results)
        })

        viewModel.recentMovieList.observe(viewLifecycleOwner, {
            recentMovieAdapter.setList(it.results)
        })

        viewModel.topRatedMovieList.observe(viewLifecycleOwner, {
           topRatedMovieAdapter.setList(it.results)
        })

        viewModel.popularTvList.observe(viewLifecycleOwner, {
           popularTvAdapter.setList(it.results)
        })

        viewModel.topRatedTvList.observe(viewLifecycleOwner, {
         topRatedTvAdapter.setList(it.results)
        })

        onPopularMoviesClick()
    }


    fun setupRecyclerView(){

        movieAdapter = MovieAdapter()
        recentMovieAdapter = RecentMovieAdapter()
        topRatedMovieAdapter = TopRatedMovieAdapter()
        popularTvAdapter = PopularTvAdapter()
        topRatedTvAdapter = TopRatedTvAdapter()
        binding.popularMovieRecyclerView.apply {
            adapter = movieAdapter
            set3DItem(true)
            setAlpha(true)
            setInfinite(true)
        }


        binding.recentRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = recentMovieAdapter
        }

        binding.topRatedMoviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = topRatedMovieAdapter
        }

        binding.popularTvRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = popularTvAdapter
        }

        binding.topRatedTvRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = topRatedTvAdapter
        }

    }

    fun onPopularMoviesClick(){
        movieAdapter.setOnPopularMovieItemClick { movie ->
            val intent = Intent(activity, ShowActivity::class.java)
            intent.putExtra("title",movie.title)
            intent.putExtra("date",movie.release_date)
            intent.putExtra("vote",movie.vote_average.toString())
            intent.putExtra("overview",movie.overview)
            intent.putExtra("image",movie.poster_path)
            intent.putExtra("id",movie.id.toString())
            Log.d("PopularShowpass", "${movie.popularity}")
            startActivity(intent)
        }
    }



}