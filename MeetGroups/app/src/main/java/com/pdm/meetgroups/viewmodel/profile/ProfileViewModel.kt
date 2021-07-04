package com.pdm.meetgroups.viewmodel.profile

import android.graphics.Bitmap
import com.pdm.meetgroups.model.entities.Journal
import java.util.ArrayList

typealias JournalList = ArrayList<Journal>

interface ProfileViewModel : JournalListViewModelAdapter{
    fun getUserImage() : Bitmap?

    fun getUserBio() : String?

    fun getUserNickname() : String

    fun loadLocalUser()
}