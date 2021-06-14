package com.pdm.meetgroups.model.dbmanager.firestoremodel

import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.model.entities.UserContext

interface JournalFirestoreModel {
    suspend fun createJournal (journal : Journal) : Boolean

    suspend fun closeJournal (journal : Journal) : Boolean

    //TODO checks to use this methods only if admin
    suspend fun addParticipant (journal : Journal, user : UserContext) : Boolean

    suspend fun removeParticipant (journal : Journal, user : UserContext) : Boolean

    suspend fun loadParticipants (journal : Journal) : ArrayList<String>?

    suspend fun updateJournalTitle (journal: Journal) : Boolean

    suspend fun loadJournalPosts (journal: Journal) : ArrayList<Post>?
}