package com.pdm.meetgroups.viewmodel

import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.observers.DBObserver

class DBObserverHandler(vm : ViewModel) : DBObserver {
    private val viewModel = vm

    override fun notifyLoadParticipants(result: Boolean, participants: List<UserContext>?) {
        TODO("Not yet implemented")
    }

    override fun notifyLoadJournalPosts(result: Boolean, posts: List<Post>?) {
        TODO("Not yet implemented")
    }

    override fun notifyCloseJournal(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyAddParticipant(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyRemoveParticipant(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyUpdateJournalTitle(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyCreateUser(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyDeleteUser(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyUpdateUserBio(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyUpdateUserAddNewJournalLink(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyUpdateUserImage(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyChangeUserState(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyCreatePost(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyDeletePost(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifySignUpUser(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifySignInUser(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyUpdateAuthPassword(result: Boolean) {
        TODO("Not yet implemented")
    }

    override fun notifyUpdateStoredUserImage(result: Boolean) {
        TODO("Not yet implemented")
    }
}