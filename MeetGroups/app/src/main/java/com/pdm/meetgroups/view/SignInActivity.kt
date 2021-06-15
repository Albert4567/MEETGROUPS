package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.*
import com.pdm.meetgroups.model.dbmanager.AuthentificationModelImpl
import com.pdm.meetgroups.model.dbmanager.FirestoreModel
import com.pdm.meetgroups.model.dbmanager.FirestoreModelImpl
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignInActivity : AppCompatActivity() {
    private lateinit var authModelImpl : AuthentificationModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        authModelImpl = AuthentificationModelImpl()
    }

    fun signIn(view : View?) {
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
    }
}