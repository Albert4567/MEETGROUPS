package com.pdm.meetgroups.observers

import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.model.entities.UserContext

interface DBObserver {
    fun notifyLoadParticipants (result : Boolean, participants : List<UserContext>?)

    fun notifyLoadJournalPosts (result : Boolean, posts : List<Post>?)

    fun notifyCloseJournal (result : Boolean)

    fun notifyAddParticipant (result : Boolean)

    fun notifyRemoveParticipant (result : Boolean)

    fun notifyUpdateJournalTitle (result : Boolean)

    fun notifyCreateUser (result : Boolean)

    fun notifyDeleteUser (result : Boolean)

    fun notifyUpdateUserBio (result : Boolean)

    fun notifyUpdateUserAddNewJournalLink (result : Boolean)

    fun notifyUpdateUserImage (result : Boolean)

    fun notifyChangeUserState (result : Boolean)

    fun notifyCreatePost (result: Boolean)

    fun notifyDeletePost (result: Boolean)

    fun notifySignUpUser (result : Boolean)

    fun notifySignInUser (result : Boolean)

    fun notifyUpdateAuthPassword (result : Boolean)

    fun notifyUpdateStoredUserImage (result : Boolean)
}