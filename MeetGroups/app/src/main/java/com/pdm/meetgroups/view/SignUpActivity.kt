package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.ActivitySignUpBinding
import com.pdm.meetgroups.viewmodel.login.LoginViewModelImpl

class SignUpActivity : AppCompatActivity() {
    private val loginVMImpl : LoginViewModelImpl by viewModels()
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
    }

    fun signIn(view : View?) {
    }

    fun signUp(view : View?) {
    }
}