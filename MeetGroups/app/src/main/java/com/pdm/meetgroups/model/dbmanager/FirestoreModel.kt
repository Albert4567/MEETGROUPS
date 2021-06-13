package com.pdm.meetgroups.model.dbmanager

import android.net.Uri
import com.pdm.meetgroups.model.entities.*

interface FirestoreModel {
    fun instantiateUserModel (uid : String)

    //UsersDedicatedMethod
    //    create a user document on firebase with no journals
    suspend fun createUser (user : UserContext) : Boolean

    suspend fun deleteUser () : Boolean

    suspend fun updateUserBio (newBio : String) : Boolean

    suspend fun updateUserAddNewJournalLink (user : UserContext, journal: Journal) : Boolean

    suspend fun updateUserImage (newImageUri : Uri, uid : String) : Boolean

    suspend fun changeUserState () : Boolean

    //JournalsDedicatedMethods
    suspend fun createJournal (journal : Journal) : Boolean

    suspend fun closeJournal (journal : Journal) : Boolean

    //TODO checks to use this methods only if admin
    suspend fun addParticipant (journal : Journal, user : UserContext) : Boolean

    suspend fun removeParticipant (journal : Journal, user : UserContext) : Boolean

    suspend fun loadParticipants (journal : Journal) : List<UserContext>?

    suspend fun updateJournalTitle (journal: Journal) : Boolean

    suspend fun loadJournalPosts (journal: Journal) : List<Post>?

    //PostsDedicatedMethods
    suspend fun createPost (journal : Journal, post : Post) : Boolean

    suspend fun deletePost (journal : Journal, post : Post) : Boolean
}