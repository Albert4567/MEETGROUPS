package com.pdm.meetgroups.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.pdm.meetgroups.view.SignUpActivity

class AuthentificationController {
    private val TAG : String = "UserAuthController"
    private var mAuth : FirebaseAuth

    constructor(auth : FirebaseAuth) {
        mAuth = auth
    }

    //TODO("controllare comportamento se offline")
    fun signUpUser (email : String, password : String) : Boolean {
        var outcome : Boolean = false
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d(TAG, "createUserWithEmail:success")
                val user = mAuth.currentUser
                //TODO("updateUIAndChangeActivity(user)")
                outcome = true;
            }
            .addOnFailureListener { it ->
                Log.e(TAG, it.toString())
                //TODO("displayError()")
                outcome = false
            }
        return outcome
    }

    fun signInUser (email : String, password : String) : Boolean {
        var outcome : Boolean = false
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d(TAG, "signInUser:success")
                TODO("updateUIAndChangeActivity()")
                outcome = true
            }
            .addOnFailureListener { it ->
                Log.e(TAG, it.toString())
                TODO("displayError()")
                outcome = false
            }
        return outcome
    }

    fun checkIfSignedIn () : Boolean { return mAuth.currentUser != null }
}