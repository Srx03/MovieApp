package com.example.movieapp.ui.activitis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.movieapp.R
import com.example.movieapp.adapter.home.MovieAdapter
import com.example.movieapp.adapter.home.RecentMovieAdapter
import com.example.movieapp.databinding.ActivityMainBinding

import com.example.movieapp.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findNavController(R.id.fragmentContainerView).navigate(R.id.homeFragment)
        setupTabBar()


    }

    private fun setupTabBar(){
        binding.bottomNavbar.setOnItemSelectedListener {
            when(it){
                R.id.home -> findNavController(R.id.fragmentContainerView).navigate(R.id.homeFragment)
                R.id.search -> findNavController(R.id.fragmentContainerView).navigate(R.id.searchFragment)
                R.id.watchlist -> findNavController(R.id.fragmentContainerView).navigate(R.id.watchListFragment)
                R.id.profile -> findNavController(R.id.fragmentContainerView).navigate(R.id.profileFragment)
                R.id.comingSoon -> findNavController(R.id.fragmentContainerView).navigate(R.id.comingSoonFragment)
            }
        }
    }


}