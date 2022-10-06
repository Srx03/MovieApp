package com.example.movieapp.ui.comingsoon


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.movieapp.ui.comingsoon.adapter.ComingSoonGenreAdapter
import com.example.movieapp.ui.comingsoon.adapter.ComingSoonMovieAdapter
import com.example.movieapp.ui.comingsoon.adapter.ComingSoonTvAdapter
import com.example.movieapp.databinding.FragmentComingSoonBinding
import com.example.movieapp.models.movie.MovieResult
import com.example.movieapp.models.tv.TVResults
import com.example.movieapp.util.*
import com.google.android.material.tabs.TabLayout
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComingSoonFragment : Fragment() {

    private var _binding: FragmentComingSoonBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ComingSoonViewModel by activityViewModels()
    private lateinit var comingSoonMovieAdapter: ComingSoonMovieAdapter
    private lateinit var comingSoonTvAdapter: ComingSoonTvAdapter
    private var isFirstPrinted: Boolean = false
    private var onFirstLoadTab: Boolean = false
    private lateinit var genreAdapter: ComingSoonGenreAdapter
    private var onTabSelectedListener: TabLayout.OnTabSelectedListener? = null
    private var _movieResult: MovieResult? = null
    private var _tvResult: TVResults? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onFirstLoadTab = false
        isFirstPrinted = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComingSoonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!onFirstLoadTab){
            isFirstPrinted = false
            Log.d("first", isFirstPrinted.toString())
            viewModel.getComingSoonMovies()
            setupRecyclerViewMovie()
            onFirstLoadTab = true
        }

        viewModel.comingSoonMovieList.observe(viewLifecycleOwner) {
            when (it){

                is Resource.Error -> {
                    showSnackBar(message = it.message)
                }
                is Resource.Loading -> {
                }

                is Resource.Success -> {


                    setupRecyclerViewMovie()
                    comingSoonMovieAdapter.setList(it.data!!.results)
                }
                else -> Unit
            }
        }

        viewModel.comingSoonTvList.observe(viewLifecycleOwner) {
            when (it){

                is Resource.Error -> {
                    showSnackBar(message = it.message)
                }
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    setupRecyclerViewTv()
                    comingSoonTvAdapter.setList(it.data!!.results)
                }
                else -> Unit
            }
        }



        onTabSelectedListener = object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){

                    0 -> {
                        isFirstPrinted = false
                        viewModel.getComingSoonMovies()
                        binding.toolbar.title = "Coming Soon Movies"


                    }
                    1 -> {
                        isFirstPrinted = false
                        viewModel.getComingSoonTv()
                        binding.toolbar.title = "Coming Soon Tv"
                    }

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        }
        binding.tabLayout.addOnTabSelectedListener(onTabSelectedListener!!)



    }

    fun setupRecyclerViewMovie() {
        isFirstPrinted = false
        comingSoonMovieAdapter = ComingSoonMovieAdapter(
            onFirstLoad = {
                if (!isFirstPrinted) {
                    isFirstPrinted = true
                    Log.d("three", isFirstPrinted.toString())
                    binding.apply {
                        _movieResult = it
                        title.text = it.title
                        overview.text = it.overview
                        tvRelaseDate.formatUpcomingDate(it.release_date)
                        genreAdapter.setList(Genres.Genres.getMovieGenreListFromIds(it.genre_ids))
                    }
                }
            }
        )
        binding.apply {
            comingSoonRecyclerView.adapter = comingSoonMovieAdapter
            comingSoonRecyclerView.setHasFixedSize(true)
            comingSoonRecyclerView.set3DItem(true)
            comingSoonRecyclerView.setAlpha(true)

            genreAdapter = ComingSoonGenreAdapter()
                genresRv.setHasFixedSize(true)
            genresRv.adapter = genreAdapter

                    comingSoonRecyclerView.setItemSelectListener(object :
                        CarouselLayoutManager.OnSelected {
                        override fun onItemSelected(position: Int) {
                            _binding?.let {

                                val movieResult = comingSoonMovieAdapter.getSelectedItem(position)
                                _movieResult = movieResult
                                title.text = movieResult.title
                                // Setting the Genre List
                                tvRelaseDate.formatUpcomingDate(movieResult.release_date)
                                genreAdapter.setList(Genres.Genres.getMovieGenreListFromIds(movieResult.genre_ids))
                                overview.text = movieResult.overview
                            }
                        }
                    })
        }
    }

    fun setupRecyclerViewTv() {
        isFirstPrinted = false
        comingSoonTvAdapter = ComingSoonTvAdapter(
            onFirstLoad = {
                if (!isFirstPrinted) {
                    isFirstPrinted = true
                    binding.apply {
                        _tvResult = it
                        title.text = it.name
                        overview.text = it.overview
                        tvRelaseDate.formatUpcomingTv(it.first_air_date)
                        genreAdapter.setList(Genres.Genres.getTvGenreListFromIds(it.genre_ids))
                    }
                }
            }
        )
        binding.apply {
            comingSoonRecyclerView.adapter = comingSoonTvAdapter
            comingSoonRecyclerView.setHasFixedSize(true)
            comingSoonRecyclerView.set3DItem(true)
            comingSoonRecyclerView.setAlpha(true)

            genreAdapter = ComingSoonGenreAdapter()
            genresRv.setHasFixedSize(true)
            genresRv.adapter = genreAdapter
                // Setting Genre Recyclerview

                    comingSoonRecyclerView.setItemSelectListener(object :
                        CarouselLayoutManager.OnSelected {
                        override fun onItemSelected(position: Int) {
                            _binding?.let {

                                val tvResult = comingSoonTvAdapter.getSelectedItem(position)
                                _tvResult = tvResult
                                title.text = tvResult.name
                                // Setting the Genre List
                                tvRelaseDate.formatUpcomingTv(tvResult.first_air_date)
                                genreAdapter.setList(Genres.Genres.getMovieGenreListFromIds(tvResult.genre_ids))
                                overview.text = tvResult.overview
                            }
                        }
                    })
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        onTabSelectedListener = null
        onFirstLoadTab = false
        isFirstPrinted = false
    }

}