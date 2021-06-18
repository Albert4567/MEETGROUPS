package com.pdm.meetgroups.viewmodel.login

import android.app.Activity

interface LoginViewModel {
    fun signUpUser (email : String, password : String, nickname : String)

    fun signInUser (email : String, password : String)

    fun loadView (view : Activity)
}