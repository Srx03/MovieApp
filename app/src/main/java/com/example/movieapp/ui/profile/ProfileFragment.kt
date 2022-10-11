package com.example.movieapp.ui.profile


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
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.ui.profile.adapter.WatchlistMovieAdapter
import com.example.movieapp.ui.profile.adapter.WatchlistTvAdapter
import com.example.movieapp.databinding.FragmentProfileBinding
import com.example.movieapp.ui.activitis.LogActivity
import com.example.movieapp.util.RegisterValidation
import com.example.movieapp.util.Resource
import com.example.movieapp.util.showSnackBar
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

    private var toastHelperEmail: Boolean = false
    private var toastHelperPassword: Boolean = false
    private var toastHelperUsername: Boolean = false
    private var toastHelperMovieDelete: Boolean = false
    private var toastHelperTvDelete: Boolean = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        toastHelperEmail = false
        toastHelperPassword = false
        toastHelperUsername = false
        toastHelperMovieDelete = false
        toastHelperTvDelete = false

        viewModelWatchList.getWatchListMovie()
        viewModelWatchList.getWatchListTv()

       viewModelWatchList.movieList.observe(viewLifecycleOwner){movieArrayList ->

           if (movieArrayList.isNotEmpty()){
               binding.rvWatchListMovie.isGone = false
               binding.emptyWatchlistMovie.isGone = true
               binding.tvMovieDelete.isGone = false
               watchlistMovieAdapter.setList(movieArrayList)
           }else{
               binding.rvWatchListMovie.isGone = true
               binding.emptyWatchlistMovie.isGone = false
               binding.tvMovieDelete.isGone = true
           }


       }

        viewModelWatchList.errorState.observe(viewLifecycleOwner) { error ->
            showSnackBar(message = error)
        }
        viewModelWatchList.loadingState.observe(viewLifecycleOwner) {
        }


        viewModelWatchList.tvList.observe(viewLifecycleOwner){tvArrayList ->

            if (tvArrayList.isNotEmpty()){
                binding.rvWatchListTv.isGone = false
                binding.emptyWatchlistTv.isGone = true
                binding.tvTvDelete.isGone = false
                watchlistTvAdapter.setList(tvArrayList)
            }else{
                binding.rvWatchListTv.isGone = true
                binding.emptyWatchlistTv.isGone = false
                binding.tvTvDelete.isGone = true
            }


        }

        viewModelWatchList.errorStateTv.observe(viewLifecycleOwner) { error ->
            showSnackBar(message = error)
        }
        viewModelWatchList.loadingStateTv.observe(viewLifecycleOwner) {
        }



        viewModel.getDataFromFirebase()
        observeLiveData()

        binding.apply {
            btnSaveEmail.setOnClickListener{

                MaterialAlertDialogBuilder(requireContext())
                    .setMessage("Are you sure you want to change email?")
                    .setPositiveButton("Edit") { _, _ ->

                        toastHelperEmail = true
                        val email = etEmail.text.toString().trim()
                        viewModel.saveEditEmail(email)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()

            }

            btnSavePassword.setOnClickListener {

                MaterialAlertDialogBuilder(requireContext())
                    .setMessage("Are you sure you want to change password?")
                    .setPositiveButton("Edit") { _, _ ->
                        toastHelperPassword = true
                        val password = etPassword.text.toString()
                        viewModel.saveEditPassword(password)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

            btnSaveUsername.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage("Are you sure you want to change username?")
                    .setPositiveButton("Edit") { _, _ ->
                        toastHelperUsername = true

                        val userName = etUsername.text.toString().trim()
                        viewModel.saveEditUser(userName)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()


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

        viewModel.editEmail.observe(viewLifecycleOwner){
                when(it){
                    is Resource.Loading ->{
                        binding.btnSaveEmail.startAnimation()
                    }

                    is Resource.Error ->{
                        binding.btnSaveEmail.revertAnimation()
                        showSnackBar(message = it.message)
                        binding.btnSaveEmail.background = ContextCompat.getDrawable(requireContext(), R.drawable.btnbackground)
                    }

                    is Resource.Success ->{
                        if(toastHelperEmail){
                            Toast.makeText(requireContext(),"Succesfully Changed Email", Toast.LENGTH_SHORT).show()
                        }
                        binding.btnSaveEmail.revertAnimation()
                        binding.btnSaveEmail.background = ContextCompat.getDrawable(requireContext(), R.drawable.btnbackground)


                    }
                    else -> Unit

                }
            }


        viewModel.editPassword.observe(viewLifecycleOwner){
                when(it){
                    is Resource.Loading ->{
                        binding.btnSavePassword.startAnimation()
                    }

                    is Resource.Error ->{
                        binding.btnSavePassword.revertAnimation()
                        showSnackBar(message = it.message)
                        binding.btnSavePassword.background = ContextCompat.getDrawable(requireContext(), R.drawable.btnbackground)
                    }

                    is Resource.Success ->{
                        if(toastHelperPassword){
                            Toast.makeText(requireContext(),"Succesfully Changed Password", Toast.LENGTH_SHORT).show()
                        }
                        binding.btnSavePassword.revertAnimation()
                        binding.btnSavePassword.background = ContextCompat.getDrawable(requireContext(), R.drawable.btnbackground)

                    }
                    else -> Unit

                }
            }


        viewModel.editUsername.observe(viewLifecycleOwner){
                when(it){
                    is Resource.Loading ->{
                        binding.btnSaveUsername.startAnimation()
                    }

                    is Resource.Error ->{
                        binding.btnSaveUsername.revertAnimation()
                        showSnackBar(message = it.message)
                        binding.btnSaveUsername.background = ContextCompat.getDrawable(requireContext(), R.drawable.btnbackground)
                    }

                    is Resource.Success ->{
                        if(toastHelperUsername){
                            Toast.makeText(requireContext(),"Succesfully Changed Username", Toast.LENGTH_LONG).show()
                        }
                        binding.btnSaveUsername.revertAnimation()
                        binding.btnSaveUsername.background = ContextCompat.getDrawable(requireContext(), R.drawable.btnbackground)

                    }
                    else -> Unit

                }
            }


        viewModel.errorStateEdit.observe(viewLifecycleOwner) { error ->
            showSnackBar(message = error)
        }
        viewModel.loadingStateEdit.observe(viewLifecycleOwner) {
        }



            viewModelWatchList.movieDelete.observe(viewLifecycleOwner){
                when(it){
                    is Resource.Loading ->{
                    }

                    is Resource.Error ->{
                        showSnackBar(message = it.message!!)
                    }

                    is Resource.Success ->{

                        if(toastHelperMovieDelete)
                        Toast.makeText(requireContext(),"Succesfully deleted movie", Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit

                }
            }

        viewModelWatchList.tvDelete.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                }

                is Resource.Error ->{
                    showSnackBar(message = it.message!!)
                }

                is Resource.Success ->{
                    if (toastHelperTvDelete)
                    Toast.makeText(requireContext(),"Succesfully deleted tv", Toast.LENGTH_SHORT).show()

                }
                else -> Unit

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
            viewModel.saveImage(profilePictureUri!!)
            binding.imgProfile.load(profilePictureUri)
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
            showSnackBar(message = error)
        }
        viewModel.loadingState.observe(viewLifecycleOwner) {
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

            toastHelperTvDelete = true
            viewModelWatchList.deleteTv(it)
        }
    }

    fun deleteMovie(){
        watchlistMovieAdapter.deleteWatchlistMovieItemClick {

            toastHelperMovieDelete = true
            viewModelWatchList.deleteMovie(it)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }






}