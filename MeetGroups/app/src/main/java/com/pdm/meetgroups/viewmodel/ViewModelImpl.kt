package com.pdm.meetgroups.viewmodel

import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.observers.ViewModelObserver

//TODO finire di implementare l'observer
class ViewModelImpl(vObserver : ViewModelObserver) : ViewModel {
    private val dbObserver = DBObserverHandler(this)
    private val viewObserver = vObserver
    private val model = ModelImpl(dbObserver)

    private val journalViewModel = JournalViewModel(model)

    override fun createJournal(journal: Journal) {
        model.createJournal(journal)
    }

    override fun closeJournal(journal: Journal) {
        model.closeJournal(journal)
    }

    override fun addParticipant(journal: Journal, user: UserContext) {
        model.addParticipant(journal, user)
    }

    override fun removeParticipant(journal: Journal, user: UserContext) {
        model.removeParticipant(journal, user)
    }

    override fun loadParticipants(journal: Journal) {
        model.loadParticipants(journal)
    }

    fun loadParticipants () {
        viewObserver.notifyLoadParticipantS()
    }

    override fun updateJournalTitle(journal: Journal) {
        model.updateJournalTitle(journal)
    }

    override fun loadJournalPosts(journal: Journal) {
        model.loadJournalPosts(journal)
    }

    override fun createPost(journal: Journal, post: Post) {
        TODO("Not yet implemented")
    }

    override fun deletePost(journal: Journal, post: Post) {
        TODO("Not yet implemented")
    }

}