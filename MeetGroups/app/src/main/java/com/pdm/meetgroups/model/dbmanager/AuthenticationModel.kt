package com.pdm.meetgroups.model.dbmanager

import com.google.android.gms.tasks.Task

interface AuthenticationModel {
    fun signUpUser (email : String, password : String)

    fun signInUser (email : String, password : String)

    fun signOutUser ()

    fun getCurrentUserUID () : String?

    fun checkIfSignedIn () : Boolean

    fun deleteAuthUser () : Task<Void>

    fun updateAuthPassword (newPassword : String)

}