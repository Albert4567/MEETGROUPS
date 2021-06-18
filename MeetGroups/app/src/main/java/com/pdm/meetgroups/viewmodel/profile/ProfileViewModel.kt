package com.pdm.meetgroups.viewmodel.profile

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.pdm.meetgroups.model.entities.Journal
import java.util.ArrayList

typealias JournalList = ArrayList<Journal>

interface ProfileViewModel {
    fun getJournals(): LiveData<JournalList>

    fun getUserImage() : Bitmap?

    fun getUserBio() : String?

    fun getUserNickname() : String
}