package com.pdm.meetgroups.viewmodel.profile

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.pdm.meetgroups.model.entities.Journal
import java.util.ArrayList

typealias JournalList = ArrayList<Journal>

interface ProfileViewModel : JournalListViewModelAdapter{
    fun getImage() : LiveData<Bitmap>

    fun getBio() : LiveData<String>

    fun getNickname() : LiveData<String>

    fun loadLocalUser()
}