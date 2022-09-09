package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.adapter.show.ActorShowAdapter
import com.example.movieapp.adapter.show.SimilarAdapter
import com.example.movieapp.databinding.FragmentShowBinding
import com.example.movieapp.ui.viewmodel.ShowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowFragment: Fragment() {

    private var _binding: FragmentShowBinding? = null
    private val binding get() = _binding!!

    private lateinit var actorShowAdapter: ActorShowAdapter
    private lateinit var similarAdapter: SimilarAdapter
    private val  viewModel: ShowViewModel by activityViewModels()


    private lateinit var id: String
    private lateinit var idTv: String
    private lateinit var isMovie: String
    private var isClicked: Boolean = false
    private var isMovieHelp: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getOnPopularClickData()


        if (isMovie == "0"){
            viewModel.movieCreditsList.observe(viewLifecycleOwner) {
                actorShowAdapter.setList(it.cast)
            }

            viewModel.similarMovieList.observe(viewLifecycleOwner) {
                similarAdapter.setList(it.results)
            }

            viewModel.movieDetailList.observe(viewLifecycleOwner) {
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + it.poster_path)
                    .into(binding.youtubePlayerView)
                binding.tvTitle.text = it.title
                binding.tvYear.text = it.release_date
                binding.tvOverview.text = it.overview
                binding.tvRating.text = String.format("%.1f", it.vote_average)
                binding.tvGenres.text = it.genres.joinToString("  /  ") { it.name }
            }
        }else{
            viewModel.tvCreditsList.observe(viewLifecycleOwner) {
                actorShowAdapter.setListTv(it.cast)
            }

            viewModel.similarTvList.observe(viewLifecycleOwner) {
                similarAdapter.setListTv(it.results)
            }
            viewModel.tvDetailList.observe(viewLifecycleOwner) {
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + it.poster_path)
                    .into(binding.youtubePlayerView)
                binding.tvTitle.text = it.name
                binding.tvYear.text = it.first_air_date
                binding.tvOverview.text = it.overview
                binding.tvRating.text = String.format("%.1f", it.vote_average)
                binding.tvGenres.text = it.genres.joinToString("  /  ") { it.name }
            }
        }


        setUpRecyclerView()
        setUpClickListeners()
        setOnActorClick()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getOnPopularClickData() {
        val args = this.arguments
        id = args?.getString("id").toString()
        idTv = args?.getString("idTv").toString()
        isMovie = args?.getString("isMovie").toString()

        if(isMovie == "0"){
            isMovieHelp = true
            viewModel.getSimilarMovie(id)
            viewModel.getMovieCredits(id)
            viewModel.getMoiveDetail(id)
        }
        else {
            isMovieHelp = false
            viewModel.getTvDetail(idTv)
            viewModel.getTvCredits(idTv)
            viewModel.getSimilarTv(idTv)
        }


    }

    private fun setUpRecyclerView(){
        actorShowAdapter = ActorShowAdapter(isMovie)
        similarAdapter = SimilarAdapter(isMovie)

        binding.actorsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = actorShowAdapter
        }

        binding.similarRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = similarAdapter
        }


    }

    private fun setUpClickListeners() = binding.apply {

        tvOverview.setOnClickListener {
            if (!isClicked) {
                // Expand it
                isClicked = true
                tvOverview.maxLines = 100
            } else {
                // Collapse it
                isClicked = false
                tvOverview.maxLines = 4
            }
        }
    }

    private fun setOnActorClick(){

        if(isMovie == "0"){
            actorShowAdapter.setOnMovieActorItemClick { movie ->
                val bundle = Bundle().apply {
                    putString("id", movie.id.toString())
                }
                findNavController().navigate(R.id.action_showFragment_to_actorFragment, bundle)
            }
        }else{
            actorShowAdapter.setOnTvActorItemClick { tv ->
                val bundle = Bundle().apply {
                    putString("id", tv.id.toString())
                }
                findNavController().navigate(R.id.action_showFragment_to_actorFragment, bundle)
            }
        }


    }



}