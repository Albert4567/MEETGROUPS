package com.pdm.meetgroups.viewmodel.login

interface LoginViewModel {
    fun signUpUser (email : String, password : String)

    fun signInUser (email : String, password : String)
}