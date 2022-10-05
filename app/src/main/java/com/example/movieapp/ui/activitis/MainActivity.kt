package com.example.movieapp.ui.activitis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
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

        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavbar.setupWithNavController(navController)




    }


}