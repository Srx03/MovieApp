package com.example.movieapp.util



sealed class RegisterValidation(){
    object Succes: RegisterValidation()
    data class  Failed(val message: String): RegisterValidation()

}

data class LoginFieldsState(
    val email: RegisterValidation,
    val password: RegisterValidation

)

data class RegisterFieldsState(
    val email: RegisterValidation,
    val password: RegisterValidation,
    val userName: RegisterValidation
)


//for each chech

data class EmailFieldsState(
    val email: RegisterValidation
)

data class PasswordFieldsState(
    val password: RegisterValidation
)

data class UsernameFieldsState(
    val userName: RegisterValidation
)

