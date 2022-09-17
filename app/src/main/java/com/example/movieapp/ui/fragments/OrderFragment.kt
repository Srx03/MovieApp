package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.movieapp.R
import com.example.movieapp.adapter.order.GenreAdapter
import com.example.movieapp.adapter.order.OrderAdapter
import com.example.movieapp.databinding.FragmentOrderBinding
import com.example.movieapp.models.genres.GenreX
import com.example.movieapp.ui.viewmodel.OrderViewModel
import com.example.movieapp.util.Genres
import com.example.movieapp.util.Resource
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var genresAdapter: GenreAdapter
    private lateinit var orderAdpater: OrderAdapter

    private val  viewModel: OrderViewModel by activityViewModels()
    private var onTabSelectedListener: TabLayout.OnTabSelectedListener? = null
    private val allMovieGeneresList get() = Genres.Genres.getAllMovieGenreList()
    private val allTvGeneresList get() = Genres.Genres.getAllTvGenreList()
    private var selectedGenresList: ArrayList<GenreX> = ArrayList()
    private var _isMovie: Boolean = true
    private lateinit var shakeAnim: Animation



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shakeAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.anim)

        setUpRecyclerView()
        setUpTabLayout()

        binding.btnFindResult.setOnClickListener {
            if (selectedGenresList.isNotEmpty()) {
                val stringBuilder = StringBuilder()
                selectedGenresList.forEach { stringBuilder.append("${it.id},") }
                stringBuilder.removeSuffix(",")

                if(_isMovie){
                    viewModel.getMediaByGenres(stringBuilder.toString(), _isMovie)
                    viewModel.genreMovieList.observe(viewLifecycleOwner){
                        when(it){

                            is Resource.Loading ->{
                                binding.btnFindResult.startAnimation()
                            }

                            is Resource.Error -> {
                                binding.btnFindResult.revertAnimation()
                            }

                            is Resource.Success ->{
                                binding.btnFindResult.revertAnimation()
                                orderAdpater.setList(it.data!!.results)
                            }

                            else -> Unit

                        }
                    }
                }else{
                    viewModel.getMediaByGenres(stringBuilder.toString(), _isMovie)
                    viewModel.genreTvList.observe(viewLifecycleOwner){
                        when(it){

                            is Resource.Loading ->{
                                binding.btnFindResult.startAnimation()
                            }

                            is Resource.Error -> {
                                binding.btnFindResult.revertAnimation()
                            }

                            is Resource.Success ->{
                                binding.btnFindResult.revertAnimation()
                                orderAdpater.setListTv(it.data!!.results)
                            }

                            else -> Unit

                        }
                    }
                }

            } else {
                binding.genresRv.startAnimation(shakeAnim)
            }
        }
    }

    private fun setUpTabLayout() {
        onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectedGenresList.clear()
                if (tab?.position == 0) {
                    _isMovie = true
                    setUpRecyclerView()
                    binding.genresRv.adapter = genresAdapter
                    genresAdapter.setList(allMovieGeneresList)
                } else {
                    _isMovie = false
                    setUpRecyclerView()
                    Log.d("tv",_isMovie.toString())
                    binding.genresRv.adapter = genresAdapter
                    genresAdapter.setList(allTvGeneresList)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        }
        binding.tabLayout.addOnTabSelectedListener(onTabSelectedListener!!)
    }

    private fun setUpRecyclerView() = binding.apply {
        // Setting up genres option list Recyclerview
        genresAdapter = GenreAdapter(
            selectedStrokeColor = ContextCompat.getColor(requireContext(), R.color.main),
            unSelectedStrokeColor = ContextCompat.getColor(requireContext(), R.color.divider),
            cardBgColor = ContextCompat.getColor(requireContext(), R.color.text_primary),
            selectGenreItemClick = { genre: GenreX ->
                if (!selectedGenresList.contains(genre))
                    selectedGenresList.add(genre)
            },
            removeGenreItemClick = { genre: GenreX -> selectedGenresList.remove(genre) }
        )
        genresRv.setHasFixedSize(true)
        genresRv.adapter = genresAdapter
        genresAdapter.setList(allMovieGeneresList)

        // Setting up genres option list Recyclerview
        orderAdpater = OrderAdapter(_isMovie)
        mediaRv.apply {
            mediaRv.adapter = orderAdpater
            mediaRv.setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        onTabSelectedListener = null
    }

    }

