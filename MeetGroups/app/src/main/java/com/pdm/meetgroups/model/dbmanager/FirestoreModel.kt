package com.pdm.meetgroups.model.dbmanager

import android.location.Location
import android.net.Uri
import com.pdm.meetgroups.model.entities.*
import java.util.*
import kotlin.collections.ArrayList

interface FirestoreModel {
    fun instantiateUserModel (uid : String?)

    //UsersDedicatedMethod
    //    create a user document on firebase with no journals
    suspend fun createUser (user : UserContext) : Boolean

    suspend fun deleteUser () : Boolean

    suspend fun updateUserBio (newBio : String) : Boolean

    suspend fun updateOpenJournal (user : UserContext, name : String?) : Boolean

    suspend fun updateUserAddNewJournalLink (user : UserContext, journal: Journal) : Boolean

    suspend fun updateUserImage (newImageUri : Uri, nickname : String) : Boolean

    suspend fun updateUserLocation (location : Location) : Boolean

    suspend fun changeUserState () : Boolean

    suspend fun downloadUserInfo () : UserContext?

    suspend fun getUserClosedJournals (user : UserContext) : ArrayList<Journal>?

    //JournalsDedicatedMethods
    suspend fun createJournal (journal : Journal) : Boolean

    suspend fun closeJournal (journal : Journal) : Boolean

    suspend fun downloadJournalInfo (journalID : String) : Journal?

    suspend fun addParticipant (journal : Journal, user : UserContext) : Boolean

    suspend fun removeParticipant (journal : Journal, user : UserContext) : Boolean

    suspend fun loadJournal(journalID: String): Journal

    suspend fun loadParticipants (journal : Journal) : ArrayList<String>?

    suspend fun updateJournalTitle (journal: Journal) : Boolean

    suspend fun loadJournalPosts (journal: Journal) : ArrayList<Post>?

    suspend fun getNearJournalsAndLocations (location: Location) : Hashtable<Location, Journal>?

    //PostsDedicatedMethods
    suspend fun createPost (journal : Journal, post : Post) : Boolean

    suspend fun deletePost (journal : Journal, post : Post) : Boolean

    suspend fun getAllPosts () : ArrayList<Post>
}