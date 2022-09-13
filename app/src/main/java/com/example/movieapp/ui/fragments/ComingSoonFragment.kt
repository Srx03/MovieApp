package com.example.movieapp.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.movieapp.adapter.comingsoon.ComingSoonGenreAdapter
import com.example.movieapp.adapter.comingsoon.ComingSoonMovieAdapter
import com.example.movieapp.adapter.comingsoon.ComingSoonTvAdapter
import com.example.movieapp.databinding.FragmentComingSoonBinding
import com.example.movieapp.ui.viewmodel.ComingSoonViewModel
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
    private var onFirstLoad: Boolean = false
    private lateinit var genreAdapter: ComingSoonGenreAdapter
    private var onTabSelectedListener: TabLayout.OnTabSelectedListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComingSoonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!onFirstLoad){
            viewModel.getComingSoonMovies()
            setupRecyclerViewMovie()
            onFirstLoad = true
        }

        viewModel.comingSoonMovieList.observe(viewLifecycleOwner) {
            when (it){

                is Resource.Error -> {
                    showSnackBar(message = it.message!!)
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
                    showSnackBar(message = it.message!!)
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
        comingSoonMovieAdapter = ComingSoonMovieAdapter(
            onFirstLoad = {
                if (!isFirstPrinted) {
                    isFirstPrinted = true
                    binding.apply {
                        title.text = it.title
                        overview.text = it.overview
                        tvRelaseDate.formatUpcomingDate(it.release_date)
                        genreAdapter.setList(Genres.Genres.getMovieGenreListFromIds(it.genre_ids))
                    }
                }
            }
        )
        binding.comingSoonRecyclerView.apply {
            adapter = comingSoonMovieAdapter
            set3DItem(false)
            setAlpha(true)
            setInfinite(true)
        }
        binding.genresRv.apply {
            // Setting Genre Recyclerview
            genreAdapter = ComingSoonGenreAdapter()
            adapter = genreAdapter
            binding.apply {
                comingSoonRecyclerView.setItemSelectListener(object :
                    CarouselLayoutManager.OnSelected {
                    override fun onItemSelected(position: Int) {
                        _binding?.let {

                            val movieResult = comingSoonMovieAdapter.getSelectedItem(position)
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
    }

    fun setupRecyclerViewTv() {
        comingSoonTvAdapter = ComingSoonTvAdapter(
            onFirstLoad = {
                if (!isFirstPrinted) {
                    isFirstPrinted = true
                    binding.apply {
                        title.text = it.name
                        overview.text = it.overview
                        tvRelaseDate.formatUpcomingTv(it.first_air_date)
                        genreAdapter.setList(Genres.Genres.getTvGenreListFromIds(it.genre_ids))
                    }
                }
            }
        )
        binding.comingSoonRecyclerView.apply {
            adapter = comingSoonTvAdapter
            set3DItem(false)
            setAlpha(true)
            setInfinite(true)

        }
        binding.genresRv.apply {
            // Setting Genre Recyclerview
            genreAdapter = ComingSoonGenreAdapter()
            adapter = genreAdapter
            binding.apply {
                comingSoonRecyclerView.setItemSelectListener(object :
                    CarouselLayoutManager.OnSelected {
                    override fun onItemSelected(position: Int) {
                        _binding?.let {

                            val tvResult = comingSoonTvAdapter.getSelectedItem(position)
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
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        onTabSelectedListener = null
    }

}