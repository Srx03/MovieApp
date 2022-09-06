package com.example.movieapp.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.R
import com.example.movieapp.adapter.comingsoon.ComingSoonGenreAdapter
import com.example.movieapp.adapter.comingsoon.ComingSoonMovieAdapter
import com.example.movieapp.databinding.FragmentComingSoonBinding
import com.example.movieapp.models.movie.MovieResult
import com.example.movieapp.ui.viewmodel.ComingSoonViewModel
import com.example.movieapp.util.Genres
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComingSoonFragment : Fragment() {

    private var _binding: FragmentComingSoonBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ComingSoonViewModel by activityViewModels()
    private lateinit var comingSoonMovieAdapter: ComingSoonMovieAdapter
    private var isFirstPrinted: Boolean = false
    private lateinit var genreAdapter: ComingSoonGenreAdapter
    private var _movieResult: MovieResult? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComingSoonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerViewMovie()

        viewModel.comingSoonMovieList.observe(viewLifecycleOwner, {
            comingSoonMovieAdapter.setList(it.results)
        })
    }

    fun setupRecyclerViewMovie() {
        comingSoonMovieAdapter = ComingSoonMovieAdapter()
        binding.comingSoonRecyclerView.apply {
            adapter = comingSoonMovieAdapter
            set3DItem(true)
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
                                genreAdapter.setList(Genres.Genres.getMovieGenreListFromIds(movieResult.genre_ids))
                                overview.text = movieResult.overview
                        }
                    }
                })
            }
        }
    }






    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}