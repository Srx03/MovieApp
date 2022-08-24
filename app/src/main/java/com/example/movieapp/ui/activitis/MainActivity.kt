package com.example.movieapp.ui.activitis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.adapter.RecentMovieAdapter
import com.example.movieapp.databinding.ActivityMainBinding

import com.example.movieapp.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recentMovieAdapter: RecentMovieAdapter
    private lateinit var binding: ActivityMainBinding

    val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTabBar()

    }

    private fun setupTabBar(){
        binding.bottomNavbar.setOnItemSelectedListener {
            when(it){
                R.id.home -> findNavController(R.id.fragmentContainerView).navigate(R.id.homeFragment)
                R.id.search -> findNavController(R.id.fragmentContainerView).navigate(R.id.searchFragment)
                R.id.watchlist -> findNavController(R.id.fragmentContainerView).navigate(R.id.watchListFragment)
                R.id.profile -> findNavController(R.id.fragmentContainerView).navigate(R.id.profileFragment)
            }
        }
    }


}