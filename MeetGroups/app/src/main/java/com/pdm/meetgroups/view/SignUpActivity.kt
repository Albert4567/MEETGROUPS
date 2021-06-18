package com.pdm.meetgroups.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pdm.meetgroups.databinding.ActivitySignUpBinding
import com.pdm.meetgroups.viewmodel.login.LoginViewModelImpl
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val TAG : String = "SignUpActivity"
    private val loginVMImpl : LoginViewModelImpl by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginVMImpl.loadView(this)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        binding.signUpButton.setOnClickListener {
            onSignUpClick()
        }
        binding.signInButton.setOnClickListener {
            onSignInClick()
        }

        return setContentView(binding.root)
    }

    private fun onSignInClick() {
        val intent = Intent(applicationContext, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    //TODO finish check for confirm password
    private fun onSignUpClick() {
        var notEmpty = true
        if (TextUtils.isEmpty(editTextNickname.text.toString())) {
            editTextNickname.error = "inserisci un nickname"
            notEmpty = false
        }
        if (TextUtils.isEmpty(editTextEmail.text.toString())) {
            editTextEmail.error = "inserisci un email"
            notEmpty = false
        }
        if (TextUtils.isEmpty(editTextPassword.text.toString())) {
            editTextPassword.error = "inserisci una password"
            notEmpty = false
        }
        if (TextUtils.isEmpty(editTextPasswordConfirm.text.toString())) {
            editTextPasswordConfirm.error = "conferma la tua password"
            notEmpty = false
        }

        if (notEmpty) {
            loginVMImpl.signUpUser(
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString(),
                binding.editTextNickname.text.toString()
            )
        }
    }
}