package com.pdm.meetgroups.model

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.model.entities.UserContext

//TODO Add and override al the usable methods
interface Model {
    //FirestoreModel interface
    fun instantiateUserModel ()

    fun createUser (user : UserContext)

    fun deleteUser ()

    fun updateUserBio (newBio : String)

    fun updateUserAddNewJournalLink (journal: Journal)

    fun updateUserImage(newImageUri : Uri)

    fun createJournal (journal : Journal)

    fun closeJournal (journal : Journal)

    fun addParticipant (journal : Journal, user : UserContext)

    fun removeParticipant (journal : Journal, user : UserContext)

    fun loadParticipants (journal : Journal)

    fun updateJournalTitle (journal: Journal)

    fun loadJournalPosts (journal: Journal)

    fun createPost (journal : Journal, post : Post)

    fun deletePost (journal : Journal, post : Post)

    fun changeUserState ()

    //AuthentificationModel interface
    fun signUpUser (email : String, password : String)

    fun signInUser (email : String, password : String)

    fun signOutUser ()

    fun getCurrentUserUID () : String?

    fun checkIfSignedIn () : Boolean

    fun updateAuthPassword (newPassword : String)
}