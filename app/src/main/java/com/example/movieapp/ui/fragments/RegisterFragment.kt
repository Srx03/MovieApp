package com.example.movieapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.data.firebase.entities.User
import com.example.movieapp.databinding.FragmentRegisterBinding
import com.example.movieapp.ui.activitis.MainActivity
import com.example.movieapp.ui.viewmodel.RegisterViewModel
import com.example.movieapp.util.RegisterValidation
import com.example.movieapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterFragment : Fragment() {


    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val  viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnRegister.setOnClickListener{
                val user = User(
                    etEmail.text.toString().trim(),
                    etPassword.text.toString(),
                    etPhone.text.toString().trim()
                )
                viewModel.createAccountWithEmailAndPassword(user)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.register.collect{
                when(it){
                    is Resource.Loading ->{
                        binding.btnRegister.startAnimation()
                    }

                    is Resource.Error ->{
                        binding.btnRegister.revertAnimation()
                    }

                    is Resource.Success ->{
                        binding.btnRegister.revertAnimation()
                        Toast.makeText(requireContext(),"Succesfully register, please Login", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                    else -> Unit

                }
            }
        }

        lifecycleScope.launch {
            viewModel.validation.collect{ validation ->
                if (validation.email is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.etEmail.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }

                if (validation.password is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.etPassword.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }

                if (validation.userName is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.etPhone.apply {
                            requestFocus()
                            error = validation.userName.message
                        }
                    }
                }
            }
        }

        binding.mLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}