package com.example.movieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.adapter.*
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.ui.viewmodel.SearchViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val  viewModel: SearchViewModel by activityViewModels()
    private lateinit var trendingMovieAdapter: TrendingMovieAdapter
    private lateinit var trendingTvAdapter: TrendingTvAdapter
    private lateinit var trendingActorAdapter: TrendingActorAdapter
    private var onTabSelectedListener: TabLayout.OnTabSelectedListener? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.trendingMovieList.observe(viewLifecycleOwner, {
            trendingMovieAdapter.setList(it.results)
        })

        viewModel.trendingTvList.observe(viewLifecycleOwner, {
            trendingTvAdapter.setList(it.results)
        })

        viewModel.trendingActorList.observe(viewLifecycleOwner, {
            trendingActorAdapter.setList(it.results)
        })

        onTabSelectedListener = object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        viewModel.getTrendingMovies()
                        setupRecyclerViewTrendingMovie()
                    }
                    1 -> {
                        viewModel.getTrendingTv()
                        setupRecyclerViewTrendingTv()
                    }
                    2 -> {
                        viewModel.getTrendingActor()
                        setupRecyclerViewTrendingActor()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        }

        binding.tabLayout.addOnTabSelectedListener(onTabSelectedListener!!)
    }

    fun setupRecyclerViewTrendingMovie(){
        trendingMovieAdapter = TrendingMovieAdapter()
        binding.trendingRecyclerView.apply {
            layoutManager = GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false)
            adapter = trendingMovieAdapter
        }
    }

    fun setupRecyclerViewTrendingTv(){
        trendingTvAdapter = TrendingTvAdapter()
        binding.trendingRecyclerView.apply {
            layoutManager = GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false)
            adapter = trendingTvAdapter
        }
    }

    fun setupRecyclerViewTrendingActor(){
        trendingActorAdapter = TrendingActorAdapter()
        binding.trendingRecyclerView.apply {
            layoutManager = GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false)
            adapter = trendingActorAdapter
        }
    }




}