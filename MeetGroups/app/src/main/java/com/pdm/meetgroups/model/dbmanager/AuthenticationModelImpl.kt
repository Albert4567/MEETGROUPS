package com.pdm.meetgroups.model.dbmanager

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthentificationModelImpl : AuthentificationModel {
    private val TAG : String = "AuthentificationModelImpl"
    private var mAuth : FirebaseAuth = Firebase.auth


    //TODO("controllare comportamento se offline")
    override fun signUpUser (email : String, password : String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = mAuth.currentUser
                    //TODO("updateUIAndChangeActivity(user)")
                } else {
                    Log.e(TAG, "pippa")
                    //TODO("displayError()")
                }
            }
    }

    override fun signInUser (email : String, password : String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    //TODO("updateUIAndChangeActivity(user)")
                } else {
                    Log.e(TAG, "pippa")
                    //TODO("displayError()")
                }
            }
    }

    override fun signOutUser () {
        mAuth.signOut()
    }

    override fun getCurrentUserUID () : String? = mAuth.currentUser.uid

    override fun checkIfSignedIn () : Boolean = mAuth.currentUser != null

    override fun deleteAuthUser () = mAuth.currentUser.delete()

    override fun updateAuthPassword (newPassword : String) {
        //TODO("checksOnNewPassword")
        //TODO listeners?
        mAuth.currentUser.updatePassword(newPassword)
            .addOnSuccessListener { //TODO
            }
            .addOnFailureListener { //TODO
            }
    }
}