package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.ActivitySignUpBinding
import com.pdm.meetgroups.viewmodel.login.LoginViewModel

class SignInActivity : AppCompatActivity() {
    private val loginVMImpl : LoginViewModel by viewModels()
    private lateinit var binding: ActivitySignUpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
    }

    fun signIn(view : View?) {
        loginVMImpl.signUpUser(binding.editTextEmail.toString(),
            binding.editTextPasswordConfirm.toString())
    }

    fun signUp(view : View?) {
        //change activity to sign up
    }
}