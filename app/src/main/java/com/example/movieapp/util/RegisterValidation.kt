package com.example.movieapp.util



sealed class RegisterValidation(){
    object Succes: RegisterValidation()
    data class  Failed(val message: String): RegisterValidation()

}

data class RegisterFieldsState(
    val email: RegisterValidation,
    val password: RegisterValidation
)
