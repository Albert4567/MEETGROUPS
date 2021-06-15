package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pdm.meetgroups.R

class SignUpActivity : AppCompatActivity() {
    //private lateinit var authController : AuthentificationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        // = AuthentificationController(Firebase.auth)
    }
}