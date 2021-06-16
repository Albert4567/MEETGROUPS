package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< Updated upstream
import android.view.View
import androidx.activity.viewModels
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.ActivitySignUpBinding
import com.pdm.meetgroups.viewmodel.login.LoginViewModel
import com.pdm.meetgroups.viewmodel.login.LoginViewModelImpl

class SignUpActivity : AppCompatActivity() {
    private val loginVMImpl : LoginViewModelImpl by viewModels()
    private lateinit var binding: ActivitySignUpBinding
=======
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.ModelImpl

class SignUpActivity : AppCompatActivity() {
    private var model = ModelImpl()
>>>>>>> Stashed changes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
<<<<<<< Updated upstream

        binding = ActivitySignUpBinding.inflate(layoutInflater)
    }

    fun signIn(view : View?) {
        //change activity to sign in
    }

    fun signUp(view : View?) {
        //controlli se le password sono uguali
        loginVMImpl.signUpUser(binding.editTextEmail.toString(),
            binding.editTextPasswordConfirm.toString())
=======
>>>>>>> Stashed changes
    }
}