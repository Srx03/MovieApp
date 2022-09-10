package com.example.movieapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentLoginBinding
import com.example.movieapp.ui.activitis.MainActivity
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onLoginClick() {

        binding.btnLogin.setOnClickListener {

            when {

                TextUtils.isEmpty(binding.etEmail.text.toString()) -> {
                    binding.etEmail.error = "Enter an email"
                }

                !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches() -> {
                    binding.etEmail.error = "Invalid email, please enter a valid email"
                }

                TextUtils.isEmpty(binding.etPassword.text.toString()) -> {
                    binding.etPassword.error = "Please enter a password"
                }

                else -> {
                    login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                }

            }

        }

    }

    private fun onRegisterClick() {

        binding.mRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun login(email: String, password: String) {



        val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful) {

                startActivity(Intent(activity, MainActivity::class.java))
                Toast.makeText(activity, "Successful login", Toast.LENGTH_SHORT).show()

            } else {

                val message = it.exception!!.toString()
                Toast.makeText(activity, "Error: $message", Toast.LENGTH_LONG).show()

            }

        }

    }

}