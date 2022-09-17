package com.example.movieapp.util

import android.util.Log
import android.util.Patterns

fun validateEditEmail(email: String): RegisterValidation{

    if (email.isEmpty())
        return RegisterValidation.Failed("Email cannot be empty")

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Wrong email format")

    return RegisterValidation.Succes
}

fun validateEditPassword(password: String): RegisterValidation{

    if (password.isEmpty())
        return RegisterValidation.Failed("Password cannot be empty")


    if (password.length < 8)
        return RegisterValidation.Failed("Password must have 8 carachters")

    return RegisterValidation.Succes
}


fun validateEditUser(userName: String): RegisterValidation{

    if (userName.isEmpty())
        return RegisterValidation.Failed("Username cannot be empty")

    if (userName.length < 3)
        return RegisterValidation.Failed("Username must have 3 carachters")

    return RegisterValidation.Succes
}