package com.pdm.meetgroups.viewmodel

import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.model.entities.UserContext

interface ViewModel {
    fun requestCreateJournal(journal: Journal)

    fun responseCreateJournal(journal : Journal)

    fun closeJournal(journal: Journal)

    //TODO must be done only if the logged user state is ConcreteAdmin
    fun addParticipant(journal: Journal, user: UserContext)

    //TODO must be done only if the logged user state is ConcreteAdmin
    fun removeParticipant(journal: Journal, user: UserContext)

    fun loadParticipants (journal : Journal)

    //TODO must be done only if the logged user state is ConcreteAdmin
    fun updateJournalTitle(journal: Journal)

    fun loadJournalPosts(journal: Journal)

    fun createPost(journal: Journal, post: Post)

    fun deletePost(journal: Journal, post: Post)
}