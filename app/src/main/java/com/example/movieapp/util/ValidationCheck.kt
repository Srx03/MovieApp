package com.example.movieapp.util

import android.util.Patterns

fun validateEmail(email: String): RegisterValidation{
    if (email.isEmpty())
        return RegisterValidation.Failed("Email cannot be empty")

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Wrong email format")

    return RegisterValidation.Succes
}

fun validatePassword(password: String): RegisterValidation{
    if (password.isEmpty())
        return RegisterValidation.Failed("Password cannot be empty")

    if (password.length < 8)
        return RegisterValidation.Failed("Password must have 8 carachters")

    return RegisterValidation.Succes
}