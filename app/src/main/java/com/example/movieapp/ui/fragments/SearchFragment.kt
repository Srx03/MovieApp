package com.example.movieapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.adapter.search.*
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.ui.activitis.ShowActivity
import com.example.movieapp.ui.viewmodel.SearchViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val  viewModel: SearchViewModel by activityViewModels()
    private lateinit var trendingMovieAdapter: TrendingMovieAdapter
    private lateinit var trendingTvAdapter: TrendingTvAdapter
    private lateinit var trendingActorAdapter: TrendingActorAdapter
    private lateinit var searchedMovieAdapter: SearchedMovieAdapter
    private lateinit var searchedTvAdapter: SearchedTvAdapter
    private lateinit var searchedActorAdapter: SearchedActorAdapter
    private var onTabSelectedListener: TabLayout.OnTabSelectedListener? = null
    private var searchQuery: String? = null
    private var job: Job? = null
    private var categroy = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //for navigation thru fragments
        trendingMovieAdapter = TrendingMovieAdapter()
        trendingTvAdapter = TrendingTvAdapter()
        trendingActorAdapter = TrendingActorAdapter()

    }


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
        onTabSelectedListener = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        viewModel.trendingMovieList.observe(viewLifecycleOwner, {
            trendingMovieAdapter.setList(it.results)
            bindingSetupTrending()
        })

        viewModel.trendingTvList.observe(viewLifecycleOwner, {
            trendingTvAdapter.setList(it.results)
            bindingSetupTrending()
        })

        viewModel.trendingActorList.observe(viewLifecycleOwner, {
            trendingActorAdapter.setList(it.results)
            bindingSetupTrending()
        })


        viewModel.searchedMovieList.observe(viewLifecycleOwner, {
            if(it.results.isNotEmpty()){
            setupRecyclerViewSeachedMovie()
          searchedMovieAdapter.setList(it.results)
            bindingSetupSearch()
            }else binding.emptySearch.isGone = false
        })

        viewModel.searchedTvList.observe(viewLifecycleOwner, {
            if(it.results.isNotEmpty()){
            setupRecyclerViewSeachedTv()
          searchedTvAdapter.setList(it.results)
            bindingSetupSearch()
            }else binding.emptySearch.isGone = false

        })

        viewModel.searchedActorList.observe(viewLifecycleOwner, {
            if(it.results.isNotEmpty()) {
                setupRecyclerViewSeachedActor()
                searchedActorAdapter.setList(it.results)
                bindingSetupSearch()
            }else binding.emptySearch.isGone = false
        })


        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            text?.let {
                searchQuery = it.trim().toString()
                binding.apply {
                    if (it.isNotEmpty() && it.isNotBlank()) {
                        // Show searching progress bar
                        searchingProgressBar.isGone = false
                        emptySearch.isGone = true
                        trendingRecyclerView.isGone = true
                        searchedRecyclerView.isGone = true // Make it visible on getting results
                        performSearch(it.trim().toString())
                    } else {
                        viewModel.getTrendingData(categroy)
                        trendingRecyclerView.isGone = false
                        searchedRecyclerView.isGone = true
                        searchingProgressBar.isGone = true
                        emptySearch.isGone = true
                        if (categroy == 0)
                        setupRecyclerViewTrendingMovie()
                        if (categroy == 1)
                            setupRecyclerViewTrendingTv()
                        if (categroy == 2)
                            setupRecyclerViewTrendingActor()
                        // also cancel the job
                        job?.cancel()
                    }
                }
            }
        }

        onTabSelectedListener = object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        categroy = 0
                        if (searchQuery.isNullOrEmpty()){
                            viewModel.getTrendingMovies()
                            setupRecyclerViewTrendingMovie()
                            onTrendingMoviesClick()
                        }
                        searchQuery?.let {
                            if(it.isNotEmpty()){
                                viewModel.getSearchedMovie(it)
                                setupRecyclerViewSeachedMovie()
                            }
                        }
                    }
                    1 ->  {
                        categroy = 1
                        if (searchQuery.isNullOrEmpty()){
                            viewModel.getTrendingTv()
                            setupRecyclerViewTrendingTv()
                        }
                        searchQuery?.let {
                            if(it.isNotEmpty()){
                                viewModel.getSearchedTv(it)
                            }
                        }
                    }
                    2 ->  {
                        categroy = 2
                        if (searchQuery.isNullOrEmpty()){
                            viewModel.getTrendingActor()
                            setupRecyclerViewTrendingActor()
                        }
                        searchQuery?.let {
                            if(it.isNotEmpty()){
                                viewModel.getSearchedActor(it)
                            }
                        }
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



    fun setupRecyclerViewSeachedMovie(){
        searchedMovieAdapter = SearchedMovieAdapter()
        binding.searchedRecyclerView.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter =  searchedMovieAdapter
            Log.d("Srdjan","pozdrav")
            onSearchMoviesClick()
        }
    }

    fun setupRecyclerViewSeachedTv(){
        searchedTvAdapter = SearchedTvAdapter()
        binding.searchedRecyclerView.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter =  searchedTvAdapter
        }
    }

    fun setupRecyclerViewSeachedActor(){
        searchedActorAdapter = SearchedActorAdapter()
        binding.searchedRecyclerView.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter =   searchedActorAdapter
        }
    }




    private fun performSearch(searchQuery: String) {
        job?.cancel()
        job = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            // first let's wait for change in input search query
            delay(800)
            if(categroy == 0)
                viewModel.getSearchedMovie(searchQuery)
            if(categroy == 1)
                viewModel.getSearchedTv(searchQuery)
            if(categroy == 2)
                viewModel.getSearchedActor(searchQuery)
        }
    }

    private fun bindingSetupSearch(){
        binding.searchedRecyclerView.isGone = false
        binding.searchingProgressBar.isGone = true
        binding.trendingRecyclerView.isGone = true
        binding.emptySearch.isGone = true
    }

    private fun bindingSetupTrending(){
        binding.searchedRecyclerView.isGone = true
        binding.searchingProgressBar.isGone = true
        binding.trendingRecyclerView.isGone = false
        binding.emptySearch.isGone = true
    }

    fun onTrendingMoviesClick(){

        trendingMovieAdapter.setOnPopularMovieItemClick { movie ->
            val intent = Intent(activity, ShowActivity::class.java)
            intent.putExtra("isMovie","0")
            intent.putExtra("id",movie.id.toString())
            startActivity(intent)
        }

    }

    fun onSearchMoviesClick(){

        searchedMovieAdapter.setOnPopularMovieItemClick { movie ->
            val intent = Intent(activity, ShowActivity::class.java)
            intent.putExtra("isMovie","0")
            intent.putExtra("id",movie.id.toString())
            startActivity(intent)
        }

    }

    fun onTvClick(){

        trendingTvAdapter.setOnPopularTvItemClick { tv ->
            val intent = Intent(activity, ShowActivity::class.java)
            intent.putExtra("isMovie","1")
            intent.putExtra("id",tv.id.toString())
            startActivity(intent)
        }

        searchedTvAdapter.setOnPopularTvItemClick { tv ->
            val intent = Intent(activity, ShowActivity::class.java)
            intent.putExtra("isMovie","1")
            intent.putExtra("id",tv.id.toString())
            startActivity(intent)
        }

    }




}