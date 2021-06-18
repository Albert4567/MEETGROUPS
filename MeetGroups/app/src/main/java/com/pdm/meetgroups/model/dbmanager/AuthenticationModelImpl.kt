package com.pdm.meetgroups.model.dbmanager

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class AuthenticationModelImpl () : AuthenticationModel {

    private val TAG : String = "AuthentificationModelImpl"
    private var auth : FirebaseAuth = Firebase.auth


    override suspend fun signUpUser(email: String, password: String) : Boolean {
        return if (isValidPassword(password)) {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .await()
                Log.w(TAG, "Sign Up user success!")
                true
            } catch (e : Exception) {
                Log.e(TAG, "Sign Up user failed with ", e)
                false
            }
        } else
            false
    }

    override suspend fun signInUser(email: String, password: String) : Boolean {
        return try{
            auth.signInWithEmailAndPassword(email, password)
                .await()
            Log.w(TAG, "Sign in Success")
            true
        }catch (e : Exception) {
            Log.e(TAG, "Sign in Failure with ", e)
            return false
        }
    }

    override fun signOutUser () {
        auth.signOut()
    }

    override fun getCurrentUserUID () : String? = auth.currentUser?.uid

    override fun checkIfSignedIn () : Boolean = auth.currentUser != null

    override suspend fun deleteAuthUser () : Boolean {
        return try{
            auth.currentUser!!.delete()
                .await()
            Log.w(TAG, "Delete Auth User Success")
            true
        }catch (e : Exception) {
            Log.e(TAG, "Delete Auth User Failure with ", e)
            return false
        }
    }

    override suspend fun updateAuthPassword(newPassword: String) : Boolean {
        return if (isValidPassword(newPassword)) {
            try {
                auth.currentUser!!.updatePassword(newPassword)
                    .await()
                Log.w(TAG, "Update Auth Password Success!")
                true
            } catch (e : Exception) {
                Log.e(TAG, "Update Auth Password failed with ", e)
                false
            }
        } else
            false
    }

    //PASSWORD_PATTERN : almeno 8 Char di cui ,almeno 1 numero,
    // almeno 1 lettera Uppercase, almeno 1 special Char
    private fun isValidPassword(password: String) : Boolean {
        val PASSWORD_REGEX = """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!\-_?&])(?=\S+$).{8,}""".toRegex()
        return PASSWORD_REGEX.matches(password)
    }
}