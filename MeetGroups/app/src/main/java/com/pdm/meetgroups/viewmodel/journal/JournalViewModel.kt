package com.pdm.meetgroups.viewmodel.journal

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post

typealias PostList = ArrayList<Post>
typealias ParticipantList = ArrayList<String>

interface JournalViewModel {
    fun getPosts () : LiveData<PostList>

    fun showEditJournalFragment(context: Context)

    fun showGroupParticipants(view: View)

    fun showPostCreationActivity(context: Context)
}