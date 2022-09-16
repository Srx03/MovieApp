package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.home.*
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.ui.viewmodel.HomeViewModel
import com.example.movieapp.util.Resource
import com.example.movieapp.util.showSnackBar
import com.google.android.material.snackbar.Snackbar
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

        viewModel.popularMovieList.observe(viewLifecycleOwner) {
            when (it){

                is Resource.Error -> {
                    showSnackBar(
                        message = it.message!!,
                        length = Snackbar.LENGTH_INDEFINITE,
                        actionMsg = "Retry"
                    ) { viewModel.retry() }
                }
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    movieAdapter.setList(it.data!!.results)
                }
                else -> Unit
            }
        }

        viewModel.recentMovieList.observe(viewLifecycleOwner) {
            when (it){

                is Resource.Error -> {
                    showSnackBar(
                        message = it.message!!,
                        length = Snackbar.LENGTH_INDEFINITE,
                        actionMsg = "Retry"
                    ) { viewModel.retry() }
                }
                is Resource.Loading -> {}

                is Resource.Success -> {
                    recentMovieAdapter.setList(it.data!!.results)
                }

                else -> Unit
            }
        }

        viewModel.topRatedMovieList.observe(viewLifecycleOwner) {
            when (it){

                is Resource.Error -> {
                    showSnackBar(
                        message = it.message!!,
                        length = Snackbar.LENGTH_INDEFINITE,
                        actionMsg = "Retry"
                    ) { viewModel.retry() }
                }

                is Resource.Loading -> {}

                is Resource.Success -> {
                   topRatedMovieAdapter.setList(it.data!!.results)
                }

                else -> Unit
            }
        }

        viewModel.popularTvList.observe(viewLifecycleOwner) {
            when (it){
                is Resource.Error -> {
                    showSnackBar(
                        message = it.message!!,
                        length = Snackbar.LENGTH_INDEFINITE,
                        actionMsg = "Retry"
                    ) { viewModel.retry() }
                }
                is Resource.Loading -> {}

                is Resource.Success -> {
                    popularTvAdapter.setList(it.data!!.results)
                }

                else -> Unit
            }

        }

        viewModel.topRatedTvList.observe(viewLifecycleOwner) {
            when (it){

                is Resource.Error -> {
                    showSnackBar(
                        message = it.message!!,
                        length = Snackbar.LENGTH_INDEFINITE,
                        actionMsg = "Retry"
                    ) { viewModel.retry() }
                }
                is Resource.Loading -> {}

                is Resource.Success -> {
                  topRatedTvAdapter.setList(it.data!!.results)
                }

                else -> Unit
            }
        }

        onPopularMoviesClick()
        onPopularTvClick()

        binding.btnOrder.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_orderFragment)
        }


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
            val bundle = Bundle().apply {
                putString("isMovie", "0")
                putString("id", movie.id.toString())
            }
            findNavController().navigate(R.id.action_homeFragment_to_showFragment, bundle)
        }

        recentMovieAdapter.setOnPopularMovieItemClick { movie ->
            val bundle = Bundle().apply {
                putString("isMovie", "0")
                putString("id", movie.id.toString())
            }
            findNavController().navigate(R.id.action_homeFragment_to_showFragment, bundle)
        }

        topRatedMovieAdapter.setOnPopularMovieItemClick { movie ->
            val bundle = Bundle().apply {
                putString("isMovie", "0")
                putString("id", movie.id.toString())
            }
            findNavController().navigate(R.id.action_homeFragment_to_showFragment, bundle)
        }



    }

    fun onPopularTvClick(){
        popularTvAdapter.setOnPopularTvItemClick { tv ->
            val bundle = Bundle().apply {
                putString("isMovie", "1")
                putString("idTv", tv.id.toString())
            }
            findNavController().navigate(R.id.action_homeFragment_to_showFragment, bundle)
        }

        topRatedTvAdapter.setOnPopularTvItemClick { tv ->
            val bundle = Bundle().apply {
                putString("isMovie", "1")
                putString("idTv", tv.id.toString())
            }
            findNavController().navigate(R.id.action_homeFragment_to_showFragment, bundle)
        }
    }





}