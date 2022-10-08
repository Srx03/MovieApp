package com.example.movieapp.ui.show

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.ui.show.adapter.ActorShowAdapter
import com.example.movieapp.ui.show.adapter.SimilarAdapter
import com.example.movieapp.data.firebase.movie.WatchList
import com.example.movieapp.data.firebase.tv.TvWatchList
import com.example.movieapp.databinding.FragmentShowBinding
import com.example.movieapp.util.Resource
import com.example.movieapp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

    private lateinit var posterPath: String



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


        binding.apply {
            btnAddToWatchlist.setOnClickListener{

                if (isMovie == "0"){

                    val movieWatchList = WatchList(
                    id,
                    posterPath,
                    binding.tvTitle.text.toString(),
                    binding.tvRating.text.toString()
                    )
                    viewModel.saveMovie(movieWatchList)

                }
                if (isMovie == "1"){
                    val tvWatchList = TvWatchList(
                        idTv,
                        posterPath,
                        binding.tvTitle.text.toString(),
                        binding.tvRating.text.toString()
                    )
                    viewModel.saveTv(tvWatchList)

                }

            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.watchlistMovie.collect{
                when(it){
                    is Resource.Loading ->{
                        binding.btnAddToWatchlist.animate()
                    }

                    is Resource.Error ->{
                        showSnackBar(message = it.message!!)
                        binding.btnAddToWatchlist.animate()

                    }

                    is Resource.Success ->{
                        binding. btnAddToWatchlist.animate()
                        Toast.makeText(requireContext(),"Succesfully saved", Toast.LENGTH_SHORT).show()
                        Log.d("movieee","already in your watchlist")
                    }
                    else -> Unit

                }
            }
        }

        viewModel.movieExist.observe(viewLifecycleOwner){

            if (it == true)
                Toast.makeText(context,"Movie already in you watchlist", Toast.LENGTH_SHORT).show()
        }

        viewModel.tvExist.observe(viewLifecycleOwner){

            if (it == true)
                Toast.makeText(context,"Tv already in you watchlist", Toast.LENGTH_SHORT).show()
        }





        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.watchlistTv.collect{
                when(it){
                    is Resource.Loading ->{
                        binding.btnAddToWatchlist.animate()
                    }

                    is Resource.Error ->{
                        showSnackBar(message = it.message!!)
                        binding.btnAddToWatchlist.animate()
                    }

                    is Resource.Success ->{
                        binding. btnAddToWatchlist.animate()
                        Toast.makeText(context,"Succesfully saved Tv", Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit

                }
            }
        }





        if (isMovie == "0"){
            viewModel.movieCreditsList.observe(viewLifecycleOwner) {
                when (it){

                    is Resource.Error -> {
                        showSnackBar(message = it.message!!)
                    }
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {

                        actorShowAdapter.setList(it.data!!.cast)
                    }
                    else -> Unit
                }
            }

            viewModel.similarMovieList.observe(viewLifecycleOwner) {
                when (it){

                    is Resource.Error -> {
                        showSnackBar(message = it.message!!)
                    }
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {

                        similarAdapter.setList(it.data!!.results)
                    }
                    else -> Unit
                }
            }

            viewModel.movieDetailList.observe(viewLifecycleOwner) {

                when (it){

                    is Resource.Error -> {
                        showSnackBar(message = it.message!!)
                    }
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {

                        if(it.data!!.poster_path != null) {
                            Glide.with(this)
                                .load("https://image.tmdb.org/t/p/w500/" + it.data.poster_path)
                                .into(binding.imgPoster)
                            posterPath = it.data.poster_path
                        }
                        else{
                            posterPath = ""
                            Glide.with(this)
                                .load(R.drawable.noimage)
                                .into(binding.imgPoster)
                        }

                        binding.tvTitle.text = it.data.title
                        binding.tvYear.text = it.data.release_date
                        binding.tvOverview.text = it.data.overview
                        binding.tvRating.text = String.format("%.1f", it.data.vote_average)
                        binding.tvGenres.text = it.data.genres.joinToString("  /  ") { it.name }

                    }
                    else -> Unit
                }
            }
        }else{
            viewModel.tvCreditsList.observe(viewLifecycleOwner) {
                when (it){

                    is Resource.Error -> {
                        showSnackBar(message = it.message!!)
                    }
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {

                        actorShowAdapter.setListTv(it.data!!.cast)
                    }
                    else -> Unit
                }
            }

            viewModel.similarTvList.observe(viewLifecycleOwner) {
                when (it){

                    is Resource.Error -> {
                        showSnackBar(message = it.message!!)
                    }
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {

                        similarAdapter.setListTv(it.data!!.results)
                    }
                    else -> Unit
                }
            }
            viewModel.tvDetailList.observe(viewLifecycleOwner) {

                when (it){

                    is Resource.Error -> {
                        showSnackBar(message = it.message!!)
                    }
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {

                        Glide.with(this)
                            .load("https://image.tmdb.org/t/p/w500/" + it.data!!.poster_path)
                            .into(binding.imgPoster)
                        if(it.data.poster_path != null)
                            posterPath = it.data.poster_path
                        else
                            posterPath = ""
                        binding.tvTitle.text = it.data.name
                        binding.tvYear.text = it.data.first_air_date
                        binding.tvOverview.text = it.data.overview
                        binding.tvRating.text = String.format("%.1f", it.data.vote_average)
                        binding.tvGenres.text = it.data.genres.joinToString("  /  ") { it.name }
                    }
                    else -> Unit
                }

            }
        }


        setUpRecyclerView()
        setUpClickListeners()
        setOnActorClick()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}