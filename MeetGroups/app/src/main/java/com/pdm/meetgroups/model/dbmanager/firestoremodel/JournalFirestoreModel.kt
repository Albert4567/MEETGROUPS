package com.pdm.meetgroups.model.dbmanager.firestoremodel

import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.UserContext

interface JournalFirestoreModel {
    fun createJournal (journal : Journal)

    fun closeJournal (journal : Journal)

    //TODO checks to use this methods only if admin
    fun addParticipant (journal : Journal, user : UserContext)

    fun removeParticipant (journal : Journal, user : UserContext)

    fun loadParticipants (journal : Journal)

    fun updateJournalTitle (journal: Journal)

    fun loadJournalPosts (journal: Journal)
}