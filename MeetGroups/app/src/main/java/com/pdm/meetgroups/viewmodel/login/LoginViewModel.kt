package com.pdm.meetgroups.viewmodel.login

import com.pdm.meetgroups.view.SignUpActivity

interface LoginViewModel {
    fun signUpUser (email : String, password : String)

    fun signInUser (email : String, password : String)

    fun loadView (view : SignUpActivity)
}