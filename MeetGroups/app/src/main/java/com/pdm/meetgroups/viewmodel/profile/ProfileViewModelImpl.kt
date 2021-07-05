package com.pdm.meetgroups.viewmodel.profile

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Journal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModelImpl : ViewModel(),ProfileViewModel {
    private val model = ModelImpl.modelRef
    private val journals: MutableLiveData<JournalList> = MutableLiveData()
    private val bio: MutableLiveData<String> = MutableLiveData()
    private val image: MutableLiveData<Bitmap> = MutableLiveData()
    private val nickname: MutableLiveData<String> = MutableLiveData()

    override fun loadLocalUser() {
        model.instantiateLocalUser()
        loadJournals()
        loadUserBio()
        loadUserImage()
        loadUserNickname()
    }

    override fun getJournals(): LiveData<JournalList> {
        return journals
    }

    override fun getJournalBy(position: Int): Journal = journals.value!![position]

    private fun loadJournals() {
        journals.postValue(model.getUserClosedJournals())
    }

    private fun loadUserImage() {
        viewModelScope.launch(Dispatchers.IO) {
            image.postValue(model.getUserImage(model.getUser()!!.getState().nickname))
        }
    }

    override fun getImage() : LiveData<Bitmap> = image

    private fun loadUserBio() {
        bio.postValue(model.getUser()?.getState()?.bio)
    }

    override fun getBio() : LiveData<String> = bio

    private fun loadUserNickname() {
        nickname.postValue(model.getUser()?.getState()?.nickname)
    }

    override fun getNickname() : LiveData<String> = nickname
}