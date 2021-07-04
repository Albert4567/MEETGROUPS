package com.pdm.meetgroups.viewmodel.profile

import androidx.lifecycle.LiveData
import com.pdm.meetgroups.model.entities.Journal

interface JournalListViewModelAdapter {
    fun getJournals(): LiveData<JournalList>

    fun getJournalBy (position : Int) : Journal
}