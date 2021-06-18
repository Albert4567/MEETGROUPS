package com.pdm.meetgroups.viewmodel.profile

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModelImpl : ViewModel(),ProfileViewModel {
    private val model = ModelImpl.modelRef
    private val journals: MutableLiveData<JournalList> = MutableLiveData()

    override fun getJournals(): LiveData<JournalList> {
        loadJournals()
        return journals
    }

    override fun getUserImage(): Bitmap? {
        return model.getUser()?.getState()?.userImage
    }

    override fun getUserBio(): String? {
        return model.getUser()?.getState()?.bio
    }

    override fun getUserNickname(): String {
        return model.getUser()!!.getState().nickname
    }

    private fun loadJournals() {
        journals.postValue(model.getUserClosedJournals())
    }
}