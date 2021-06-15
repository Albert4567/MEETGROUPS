package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.dbmanager.AuthenticationModelImpl


class SignInActivity : AppCompatActivity() {
    private lateinit var authModelImpl : AuthenticationModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        authModelImpl = AuthenticationModelImpl()
    }

    /*fun signIn(view : View?) {
        authModelImpl.signInUser(editTextEmail.text.toString().trim(),
            editTextPassword.text.toString().trim())

        Toast.makeText(this, authModelImpl.getCurrentUserUID(),
                Toast.LENGTH_SHORT).show()
    }
    fun signUp(view : View?) {
        authModelImpl.signUpUser(editTextEmail.text.toString().trim(),
            editTextPassword.text.toString().trim())

        Toast.makeText(this, authModelImpl.getCurrentUserUID(),
            Toast.LENGTH_SHORT).show()
    }*/
}