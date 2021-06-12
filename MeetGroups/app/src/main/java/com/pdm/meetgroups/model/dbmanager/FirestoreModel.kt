package com.pdm.meetgroups.model.dbmanager

import android.net.Uri
import com.pdm.meetgroups.model.entities.*

interface FirestoreModel {
    fun instantiateUserModel (uid : String)

    //UsersDedicatedMethod
    //    create a user document on firebase with no journals
    fun createUser (user : UserContext)

    fun deleteUser ()

    fun updateUserBio (newBio : String)

    fun updateUserAddNewJournalLink (user : UserContext, journal: Journal)

    fun updateUserImage(newImageUri : Uri, uid : String)

    fun changeUserState ()

    //JournalsDedicatedMethods
    fun createJournal (journal : Journal)

    fun closeJournal (journal : Journal)

    //TODO checks to use this methods only if admin
    fun addParticipant (journal : Journal, user : UserContext)

    fun removeParticipant (journal : Journal, user : UserContext)

    fun loadParticipants (journal : Journal)

    fun updateJournalTitle (journal: Journal)

    fun loadJournalPosts (journal: Journal)

    //PostsDedicatedMethods
    fun createPost (journal : Journal, post : Post)

    fun deletePost (journal : Journal, post : Post)
}