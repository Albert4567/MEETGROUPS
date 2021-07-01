package com.pdm.meetgroups.viewmodel.journal

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.pdm.meetgroups.databinding.FragmentJournalBinding
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post

interface JournalViewModel {
    fun getPosts(): LiveData<PostList>

    fun setTitle(binding: FragmentJournalBinding)

    fun setImage(binding: FragmentJournalBinding)

    fun showEditJournalFragment(context: Context)

    fun showPostCreationActivity(context: Context)

    fun isInJournal(): Boolean

    fun loadLocalUser()
}