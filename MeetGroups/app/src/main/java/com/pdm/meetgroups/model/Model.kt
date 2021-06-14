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

    fun instantiateLocalUser ()

    suspend fun createUser (user : UserContext) : Boolean

    suspend fun deleteUser () : Boolean

    suspend fun updateUserBio (newBio : String) : Boolean

    suspend fun updateUserAddNewJournalLink (journal: Journal)

    suspend fun updateUserImage(newImageUri : Uri) : Boolean

    suspend fun changeUserState ()

    suspend fun createJournal (journal : Journal) : Boolean

    suspend fun closeJournal (journal : Journal) : Boolean

    suspend fun addParticipant (journal : Journal, user : UserContext) : Boolean

    suspend fun removeParticipant (journal : Journal, user : UserContext) : Boolean

    suspend fun loadParticipants (journal : Journal) : List<UserContext>?

    suspend fun updateJournalTitle (journal: Journal) : Boolean

    suspend fun loadJournalPosts (journal: Journal) : List<Post>?

    suspend fun createPost (journal : Journal, post : Post) : Boolean

    suspend fun deletePost (journal : Journal, post : Post) : Boolean

    //AuthentificationModel interface
    suspend fun signUpUser (email : String, password : String) : Boolean

    suspend fun signInUser (email : String, password : String) : Boolean

    fun signOutUser ()

    fun checkIfSignedIn () : Boolean

    suspend fun updateAuthPassword (newPassword : String) : Boolean
}