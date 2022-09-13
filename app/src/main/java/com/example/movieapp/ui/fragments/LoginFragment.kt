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
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentLoginBinding
import com.example.movieapp.ui.activitis.MainActivity
import com.example.movieapp.ui.viewmodel.LoginViewModel
import com.example.movieapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val  viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogin.setOnClickListener{
                val email = etEmail.text.trim().toString()
                val password = etPassword.text.toString()
                viewModel.login(email, password)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.login.collect{
                when(it){
                    is Resource.Loading -> {
                        binding.btnLogin.startAnimation()
                    }

                    is Resource.Error -> {
                        Log.d("Error",it.message!!)

                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        binding.btnLogin.revertAnimation()
                    }

                    is Resource.Success -> {
                        binding.btnLogin.revertAnimation()
                        Intent(requireActivity(), MainActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    else -> Unit
                }
            }
        }

        binding.mRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}