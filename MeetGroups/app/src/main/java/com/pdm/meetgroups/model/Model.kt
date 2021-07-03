package com.pdm.meetgroups.model

import android.location.Location
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.model.entities.UserContext
import java.util.*
import kotlin.collections.ArrayList

interface Model {
    //FirestoreModel interface
    suspend fun createUser (user : UserContext) : Boolean

    suspend fun deleteUser () : Boolean

    suspend fun updateUserBio (newBio : String) : Boolean

    suspend fun updateUserAddNewJournalLink (journal: Journal)

    suspend fun updateUserImage(newImageUri : Uri) : Boolean

    suspend fun changeUserState ()

    suspend fun updateUserLocation (location : Location) : Boolean

    fun isAdmin () : Boolean?

    suspend fun createJournal (journal : Journal) : Boolean

    suspend fun closeJournal (journal : Journal) : Boolean

    fun getJournal () : Journal?

    fun getUser () : UserContext?

    suspend fun getUser (nickname: String): UserContext?

    suspend fun getAllUsers () : ArrayList<UserContext>

    fun getUserClosedJournals () : ArrayList<Journal>?

    suspend fun addParticipant (journal : Journal, nickname: String) : Boolean

    suspend fun removeParticipant (journal : Journal, user : UserContext) : Boolean

    suspend fun loadParticipants (journal : Journal) : ArrayList<String>?

    suspend fun updateJournalTitle (journal: Journal) : Boolean

    suspend fun loadJournalPosts (journal: Journal) : ArrayList<Post>?

    suspend fun getNearJournalsAndLocations (location: Location) : Hashtable<Location, Journal>?

    suspend fun createPost (journal : Journal, post : Post, imageUris : ArrayList<Uri>?) : Boolean

    suspend fun deletePost (journal : Journal, post : Post) : Boolean

    suspend fun updateJournalImage (newImageUri : Uri) : Boolean

    //AuthentificationModel interface
    suspend fun signUpUser (email : String, password : String, user: UserContext) : Boolean

    suspend fun signInUser (email : String, password : String) : Boolean

    fun signOutUser ()

    fun checkIfSignedIn () : Boolean

    suspend fun updateAuthPassword (newPassword : String) : Boolean
}