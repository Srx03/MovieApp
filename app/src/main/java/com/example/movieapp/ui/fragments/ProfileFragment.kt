package com.example.movieapp.ui.fragments


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.adapter.watchlist.WatchlistMovieAdapter
import com.example.movieapp.adapter.watchlist.WatchlistTvAdapter
import com.example.movieapp.databinding.FragmentProfileBinding
import com.example.movieapp.ui.activitis.LogActivity
import com.example.movieapp.ui.activitis.MainActivity
import com.example.movieapp.ui.viewmodel.ProfileViewModel
import com.example.movieapp.ui.viewmodel.WatchListViewModel
import com.example.movieapp.util.RegisterValidation
import com.example.movieapp.util.Resource
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var profilePictureUri: Uri? = null

    private val  viewModel: ProfileViewModel by activityViewModels()

    private val  viewModelWatchList: WatchListViewModel by activityViewModels()
    private lateinit var watchlistMovieAdapter: WatchlistMovieAdapter
    private lateinit var watchlistTvAdapter: WatchlistTvAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelWatchList.getWatchListMovie()
        viewModelWatchList.getWatchListTv()

       viewModelWatchList.movieList.observe(viewLifecycleOwner){movieArrayList ->

           if (movieArrayList.isNotEmpty()){
               binding.rvWatchListMovie.isGone = false
               binding.emptyWatchlistMovie.isGone = true
               watchlistMovieAdapter.setList(movieArrayList)
           }else{
               binding.rvWatchListMovie.isGone = true
               binding.emptyWatchlistMovie.isGone = false
           }


       }

        viewModelWatchList.errorState.observe(viewLifecycleOwner) { error ->
            Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG).show()
        }
        viewModelWatchList.loadingState.observe(viewLifecycleOwner) { loading ->
        }


        viewModelWatchList.tvList.observe(viewLifecycleOwner){tvArrayList ->

            if (tvArrayList.isNotEmpty()){
                binding.rvWatchListTv.isGone = false
                binding.emptyWatchlistTv.isGone = true
                watchlistTvAdapter.setList(tvArrayList)
            }else{
                binding.rvWatchListTv.isGone = true
                binding.emptyWatchlistTv.isGone = false
            }


        }

        viewModelWatchList.errorStateTv.observe(viewLifecycleOwner) { error ->
            Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG).show()
        }
        viewModelWatchList.loadingStateTv.observe(viewLifecycleOwner) { loading ->
        }



        viewModel.getDataFromFirebase()
        observeLiveData()

        binding.apply {
            btnSaveEmail.setOnClickListener{
                val email = etEmail.text.toString().trim()
                viewModel.saveEditEmail(email)
            }

            btnSavePassword.setOnClickListener {
                val password = etPassword.text.toString()
                viewModel.saveEditPassword(password)
            }

            btnSaveUsername.setOnClickListener {
                val userName = etUsername.text.toString().trim()
                viewModel.saveEditUser(userName)
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.validationEmail.collect { validation ->
                if (validation.email is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.etEmail.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.validationPassword.collect { validation ->
                if (validation.password is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.etPassword.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.validationUsername.collect { validation ->
                if (validation.userName is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.etUsername.apply {
                            requestFocus()
                            error = validation.userName.message
                        }
                    }
                }

            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editEmail.collect{
                when(it){
                    is Resource.Loading ->{
                        binding.btnSaveEmail.startAnimation()
                    }

                    is Resource.Error ->{
                        binding.btnSaveEmail.revertAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }

                    is Resource.Success ->{
                        binding.btnSaveEmail.revertAnimation()
                        Toast.makeText(requireContext(),"Succesfully Changed Email", Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editPassword.collect{
                when(it){
                    is Resource.Loading ->{
                        binding.btnSavePassword.startAnimation()
                    }

                    is Resource.Error ->{
                        binding.btnSavePassword.revertAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }

                    is Resource.Success ->{
                        binding.btnSavePassword.revertAnimation()
                        Toast.makeText(requireContext(),"Succesfully Changed Password", Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editUsername.collect{
                when(it){
                    is Resource.Loading ->{
                        binding.btnSaveUsername.startAnimation()
                    }

                    is Resource.Error ->{
                        binding.btnSaveUsername.revertAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }

                    is Resource.Success ->{
                        binding.btnSaveUsername.revertAnimation()
                        Toast.makeText(requireContext(),"Succesfully Changed Username", Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit

                }
            }
        }

        onImageEdit()
        onSettingsButtonClick()
        onMovieButtonClick()
        onTvButtonClick()
        setUpRecyclerView()
        onLogOutClick()
        deleteTv()
        deleteMovie()

  }

    private fun onImageEdit(){
        binding.btnChangeImg.setOnClickListener {
            ImagePicker.with(requireActivity()).crop().createIntentFromDialog { launcherProfilePicture.launch(it) }
        }
    }

    private val launcherProfilePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        if (it.resultCode == Activity.RESULT_OK) {

            profilePictureUri = it.data?.data!!

            binding.imgProfile.load(profilePictureUri) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_profile)
                crossfade(true)
                crossfade(500)
            }
            viewModel.saveImage(profilePictureUri!!)
        }

    }

    private fun observeLiveData() {
        val userName = binding.tvUsernameFirst
        val imageView = binding.imgProfile
        val tvEmail = binding.etEmail
        val tvPassword = binding.etPassword
        val tvUsername = binding.etUsername
        viewModel.nameState.observe(viewLifecycleOwner) { name ->
            userName.setText(name)
            tvUsername.setText(name)
        }
        viewModel.emailState.observe(viewLifecycleOwner) { email ->
            tvEmail.setText(email)
        }
        viewModel.passwordState.observe(viewLifecycleOwner) { password ->
            tvPassword.setText(password)
        }
        viewModel.imageState.observe(viewLifecycleOwner) { image ->

            Glide.with(this).load(image)
                .into(imageView)
        }
        viewModel.errorState.observe(viewLifecycleOwner) { error ->
             Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG).show()
        }
        viewModel.loadingState.observe(viewLifecycleOwner) { loading ->
        }


    }

    private fun onSettingsButtonClick() {

        binding.apply {
            btnSettings.setOnClickListener {
                layoutEdit.isGone = false
                layoutTv.isGone = true
                layoutMovie.isGone = true
            }
        }
    }

    private fun onMovieButtonClick(){


        binding.apply {
            btnMovieWatchList.setOnClickListener {
                layoutEdit.isGone = true
                layoutTv.isGone = true
                layoutMovie.isGone = false
            }
        }
    }
    private fun onTvButtonClick(){
        binding.apply {
            btnTvWatchList.setOnClickListener {
                layoutEdit.isGone = true
                layoutTv.isGone = false
                layoutMovie.isGone = true
            }
        }
    }

    private fun onLogOutClick() {
        binding.btnLogout.setOnClickListener {
            viewModel.logOut()
            Intent(requireActivity(), LogActivity::class.java).also { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }


    private fun setUpRecyclerView(){
        watchlistMovieAdapter = WatchlistMovieAdapter()
        watchlistTvAdapter = WatchlistTvAdapter()

        binding.rvWatchListMovie.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = watchlistMovieAdapter
        }

        binding.rvWatchListTv.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            adapter = watchlistTvAdapter
        }

    }

    fun deleteTv(){
        watchlistTvAdapter.deleteWatchlistTvItemClick {

            viewModelWatchList.deleteTv(it)
        }
    }

    fun deleteMovie(){
        watchlistMovieAdapter.deleteWatchlistMovieItemClick {

            viewModelWatchList.deleteMovie(it)
            Log.d("test1",it.toString())
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }






}