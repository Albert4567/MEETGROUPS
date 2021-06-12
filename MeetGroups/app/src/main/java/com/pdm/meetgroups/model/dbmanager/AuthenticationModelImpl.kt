package com.pdm.meetgroups.model.dbmanager

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.meetgroups.observers.DBObserver
import java.util.regex.Matcher
import java.util.regex.Pattern

class AuthenticationModelImpl (observer : DBObserver) : AuthenticationModel {
    private val dbObserver = observer

    private val TAG : String = "AuthentificationModelImpl"
    private var auth : FirebaseAuth = Firebase.auth


    override fun signUpUser(email: String, password: String) {
        if (isValidPassword(password)) {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            dbObserver.notifySignUpUser(true)
                        } else {
                            Log.e(TAG, "user probably alredy exist")
                            dbObserver.notifySignUpUser(false)
                        }
                    }
        }
        else
            dbObserver.notifySignUpUser(false)
    }

    override fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signin Success")
                    dbObserver.notifySignInUser(true)
                } else {
                    Log.e(TAG, "signin Failure")
                    dbObserver.notifySignInUser(false)
                }
            }
    }

    override fun signOutUser () {
        auth.signOut()
    }

    override fun getCurrentUserUID () : String? = auth.currentUser.uid

    override fun checkIfSignedIn () : Boolean = auth.currentUser != null

    override fun deleteAuthUser () = auth.currentUser.delete()

    override fun updateAuthPassword(newPassword: String) {
        if (isValidPassword(newPassword)) {
            auth.currentUser.updatePassword(newPassword)
                    .addOnSuccessListener {
                        Log.d(TAG, "signin Success")
                        dbObserver.notifyUpdateAuthPassword(true)
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "signin Failure")
                        dbObserver.notifyUpdateAuthPassword(false)
                    }
        }
        else {
            Log.e(TAG, "signin Failure")
            dbObserver.notifyUpdateAuthPassword(false)
        }
    }

    //PASSWORD_PATTERN : almeno 8 Char di cui ,almeno 1 numero,
    // almeno 1 lettera Uppercase, almeno 1 special Char
    private fun isValidPassword(password: String?) : Boolean {
        val pattern : Pattern
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher : Matcher = pattern.matcher(password)
        return matcher.matches()
    }
}