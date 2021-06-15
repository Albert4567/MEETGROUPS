package com.pdm.meetgroups.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pdm.meetgroups.model.entities.Journal
import java.util.ArrayList

typealias JournalList = ArrayList<Journal>

class ProfileViewModel : ViewModel() {
    private val journals: MutableLiveData<JournalList> = MutableLiveData()

    fun getJournals(): LiveData<JournalList> = journals
}