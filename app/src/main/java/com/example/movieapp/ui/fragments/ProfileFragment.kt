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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.firebase.entities.User
import com.example.movieapp.databinding.FragmentProfileBinding
import com.example.movieapp.ui.activitis.MainActivity
import com.example.movieapp.ui.viewmodel.ProfileViewModel
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



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            Log.d("coroutineEmail", this.coroutineContext.toString())
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

            Log.d("coroutinePassword", this.coroutineContext.toString())

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
            Log.d("coroutineUsername", this.coroutineContext.toString())
            viewModel.validationUsername.collect { validation ->
                if (validation.userName is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        Log.d("coroutineClick", this.coroutineContext.toString())
                        binding.etUsername.apply {
                            requestFocus()
                            error = validation.userName.message
                        }
                    }
                }

            }

            this.cancel()

        }

        viewLifecycleOwner.lifecycleScope.launch {
            Log.d("coroutineBTN", this.coroutineContext.toString())
            viewModel.editEmail.collect{
                when(it){
                    is Resource.Loading ->{
                        binding.btnSaveEmail.startAnimation()
                    }

                    is Resource.Error ->{
                        binding.btnSaveEmail.revertAnimation()
                    }

                    is Resource.Success ->{
                        binding.btnSaveEmail.revertAnimation()

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
                    }

                    is Resource.Success ->{
                        binding.btnSavePassword.revertAnimation()

                    }
                    else -> Unit

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch  {
            viewModel.editUsername.collect{
                when(it){
                    is Resource.Loading ->{
                        binding.btnSaveUsername.startAnimation()
                    }

                    is Resource.Error ->{
                        binding.btnSaveUsername.revertAnimation()
                    }

                    is Resource.Success ->{
                        binding.btnSaveUsername.revertAnimation()

                    }
                    else -> Unit

                }
            }
        }






        onImageEdit()
        onEditButtonClick()
        onExitButtonClick()

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
                crossfade(400)
            }
            viewModel.saveImage(profilePictureUri!!)
        }

    }

    private fun observeLiveData() {
        val userName = binding.tvUsernameFirst
        val imageView = binding.imgProfile
        val tvEmail = binding.tvEmail
        val tvPassword = binding.tvPassword
        viewModel.nameState.observe(viewLifecycleOwner) { name ->
            userName.setText(name)
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
            if (error != null) Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG).show()
        }
        viewModel.loadingState.observe(viewLifecycleOwner) { loading ->
        }


    }

    private fun onEditButtonClick() {

        binding.apply {
            btnEditProfile.setOnClickListener {
                layoutCurrent.isGone = true
                layoutEdit.isGone = false
                btnEditProfile.isGone = true

            }
        }
    }

    private fun onExitButtonClick(){
        binding.apply {
            btnExit.setOnClickListener {
                layoutCurrent.isGone = false
                layoutEdit.isGone = true
                btnEditProfile.isGone = false
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }






}