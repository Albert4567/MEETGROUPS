package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.pdm.meetgroups.R
import com.pdm.meetgroups.viewmodel.AuthentificationController
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {
    private lateinit var authController : AuthentificationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        authController = AuthentificationController(Firebase.auth)
    }

    fun signUp(view : View?) {
        val outcome = authController.signUpUser(editTextEmail.text.toString().trim(),
            editTextPassword.text.toString().trim())
        if (outcome) {
            //TODO("changeActivity")
        }
        else {
            //TODO("changeActivity")
        }
        Toast.makeText(this, outcome.toString(), Toast.LENGTH_SHORT).show()
    }
}