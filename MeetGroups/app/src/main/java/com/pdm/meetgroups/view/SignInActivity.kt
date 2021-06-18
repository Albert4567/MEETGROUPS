package com.pdm.meetgroups.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import com.pdm.meetgroups.databinding.ActivitySignInBinding
import com.pdm.meetgroups.viewmodel.login.LoginViewModelImpl
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.editTextEmail
import kotlinx.android.synthetic.main.activity_sign_up.editTextPassword
import kotlinx.android.synthetic.main.activity_sign_up.signInButton

class SignInActivity : AppCompatActivity() {
    private val loginVMImpl : LoginViewModelImpl by viewModels()
    private lateinit var binding: ActivitySignInBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginVMImpl.loadView(this)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        binding.signInButton.setOnClickListener {
            onSignInClick()
        }
        binding.signUpButton.setOnClickListener {
            onSignUpClick()
        }
        return setContentView(binding.root)
    }

    private fun onSignInClick() {
        var notEmpty = true
        if (TextUtils.isEmpty(editTextEmail.text.toString())) {
            editTextNickname.error = "inserisci un nickname"
            notEmpty = false
        }
        if (TextUtils.isEmpty(editTextPassword.text.toString())) {
            editTextEmail.error = "inserisci un email"
            notEmpty = false
        }

        if (notEmpty) {
            signInButton.visibility = View.INVISIBLE
            progressBarIn.visibility = View.VISIBLE
            loginVMImpl.signInUser(binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString())
        }
    }

    private fun onSignUpClick() {
        val intent = Intent(applicationContext, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }
}