package com.example.movieapp.ui.register

import android.content.Intent
import android.os.Bundle
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
import com.example.movieapp.data.firebase.user.User
import com.example.movieapp.databinding.FragmentRegisterBinding
import com.example.movieapp.ui.activitis.MainActivity
import com.example.movieapp.util.RegisterValidation
import com.example.movieapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
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
                   emailEditText.text.toString().trim(),
                    passwordEditText.text.toString(),
                    usernameEditText.text.toString().trim()
                )
                viewModel.createAccountWithEmailAndPassword(user)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.register.collect{
                when(it){
                    is Resource.Loading ->{
                        binding.btnRegister.startAnimation()
                    }

                    is Resource.Error ->{
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        binding.btnRegister.revertAnimation()
                        binding.btnRegister.background = ContextCompat.getDrawable(requireContext(), R.drawable.btnbackground)
                    }

                    is Resource.Success ->{
                        binding.btnRegister.revertAnimation()
                        Toast.makeText(requireContext(),"Succesfully register", Toast.LENGTH_SHORT).show()
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

                if (validation.userName is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.usernameEditText.apply {
                            requestFocus()
                            binding.usernameInputLayout.helperText = validation.userName.message
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