package com.example.movieapp.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentLoginBinding
import com.example.movieapp.ui.activitis.MainActivity
import com.example.movieapp.util.RegisterValidation
import com.example.movieapp.util.Resource
import com.example.movieapp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        binding.apply {
            btnLogin.setOnClickListener{
                val email = emailEditText.text.toString().trim()
                val password = passwordEditText.text.toString()
                viewModel.login(email, password)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.login.collect{
                when(it){
                    is Resource.Loading -> {
                        binding.btnLogin.startAnimation()
                    }

                    is Resource.Error -> {
                        Log.d("Error",it.message!!)

                        showSnackBar(message = it.message)
                        binding.btnLogin.revertAnimation()
                        binding.btnLogin.background = ContextCompat.getDrawable(requireContext(), R.drawable.btnbackground)
                    }

                    is Resource.Success -> {
                        binding.btnLogin.revertAnimation()
                        Toast.makeText(requireContext(),"Succesfully Login", Toast.LENGTH_SHORT).show()
                        Intent(requireActivity(), MainActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.validation.collect{ validation ->
                if (validation.email is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.emailEditText.apply {
                            requestFocus()
                            binding.emailInputLayout.helperText = validation.email.message
                        }
                    }
                }

                if (validation.password is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.passwordEditText.apply {
                            requestFocus()
                            binding.passwordInputLayout.helperText = validation.password.message
                        }
                    }
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