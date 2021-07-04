package com.pdm.meetgroups.viewmodel.journal

import android.content.Context
import androidx.lifecycle.LiveData
import com.pdm.meetgroups.databinding.FragmentJournalBinding

interface JournalViewModel {
    fun getPosts(): LiveData<PostList>

    fun setTitle(binding: FragmentJournalBinding)

    fun setImage(binding: FragmentJournalBinding)

    fun showEditJournalFragment(context: Context)

    fun showPostCreationActivity(context: Context)

    fun isInJournal(): Boolean

    fun loadLocalUser()
}