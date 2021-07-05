package com.pdm.meetgroups.viewmodel.journal

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData

interface JournalViewModel {
    fun getPosts(): LiveData<PostList>

    fun getTitle(): LiveData<String>

    fun getImage(): LiveData<Bitmap>

    fun showEditJournalFragment(context: Context)

    fun showPostCreationActivity(context: Context)

    fun isInJournal(): Boolean

    fun loadLocalUser()
}