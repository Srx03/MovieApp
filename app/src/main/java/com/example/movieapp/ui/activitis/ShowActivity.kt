package com.example.movieapp.ui.activitis

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.adapter.show.ActorShowAdapter
import com.example.movieapp.adapter.show.SimilarAdapter
import com.example.movieapp.databinding.ShowActivityBinding
import com.example.movieapp.ui.viewmodel.ShowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowActivity: AppCompatActivity() {

    private lateinit var binding: ShowActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShowActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }




}