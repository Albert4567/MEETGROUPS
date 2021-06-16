package com.pdm.meetgroups.view

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
        setContentView(com.pdm.meetgroups.R.layout.activity_sign_up)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        binding.signUpButton.setOnClickListener {
            when {
                TextUtils.isEmpty(editTextNickname.text.toString()) -> {
                    editTextNickname.error = "inserisci un nickname"
                    return@setOnClickListener
                }
                TextUtils.isEmpty(editTextEmail.text.toString()) -> {
                    editTextEmail.error = "inserisci un email"
                    return@setOnClickListener
                }
                TextUtils.isEmpty(editTextPassword.text.toString()) -> {
                    editTextPassword.error = "inserisci una password"
                    return@setOnClickListener
                }
                TextUtils.isEmpty(editTextPasswordConfirm.text.toString()) -> {
                    editTextPasswordConfirm.error = "conferma la tua password"
                    return@setOnClickListener
                }
                else -> loginVMImpl.signUpUser(
                    binding.editTextEmail.toString(),
                    binding.editTextPassword.toString()
                )
            }
        }
    }

    fun signIn(view : View?) {

    }

    //fun signUp(view : View?) {

    //}
}