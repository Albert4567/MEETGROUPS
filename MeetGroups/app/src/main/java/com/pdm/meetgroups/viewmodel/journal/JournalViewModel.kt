package com.pdm.meetgroups.viewmodel.journal

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post

typealias PostList = ArrayList<Post>
typealias ParticipantList = ArrayList<String>

interface JournalViewModel {
    fun loadJournalPosts (journal: Journal)

    fun loadParticipants (journal : Journal)

    fun createJournal(journal: Journal)

    fun closeJournal(journal: Journal)

    fun getPosts () : LiveData<PostList>

    fun showEditJournalFragment(activity: FragmentActivity)

    fun showGroupPartecipants(view: View)

    fun showPostCreationFragment(activity: FragmentActivity)
}