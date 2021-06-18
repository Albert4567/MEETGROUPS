package com.pdm.meetgroups.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pdm.meetgroups.model.ModelImpl

class ProfileViewModelImpl : ViewModel(),ProfileViewModel {
    private val model = ModelImpl.modelRef
    private val journals: MutableLiveData<JournalList> = MutableLiveData()
    override fun getJournals(): LiveData<JournalList> {
        TODO("Not yet implemented")
    }
}