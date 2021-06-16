package com.pdm.meetgroups.model.dbmanager.firestoremodel

import android.location.Location
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.model.entities.UserContext
import java.util.*
import kotlin.collections.ArrayList

interface JournalFirestoreModel {
    suspend fun createJournal (journal : Journal) : Boolean

    suspend fun closeJournal (journal : Journal) : Boolean

    suspend fun downloadJournalInfo (journalID : String) : Journal?

    //TODO checks to use this methods only if admin
    suspend fun addParticipant (journal : Journal, user : UserContext) : Boolean

    suspend fun removeParticipant (journal : Journal, user : UserContext) : Boolean

    suspend fun loadParticipants (journal : Journal) : ArrayList<String>?

    suspend fun updateJournalTitle (journal: Journal) : Boolean

    suspend fun loadJournalPosts (journal: Journal) : ArrayList<Post>?

    suspend fun getNearJournalsAndLocations (location: Location) : Hashtable<Location, Journal>?
}