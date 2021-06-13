package com.pdm.meetgroups.model.dbmanager

interface AuthenticationModel {
    suspend fun signUpUser (email : String, password : String) : Boolean

    suspend fun signInUser (email : String, password : String) : Boolean

    fun signOutUser ()

    fun getCurrentUserUID () : String?

    fun checkIfSignedIn () : Boolean

    suspend fun deleteAuthUser () : Boolean

    suspend fun updateAuthPassword (newPassword : String) : Boolean

}