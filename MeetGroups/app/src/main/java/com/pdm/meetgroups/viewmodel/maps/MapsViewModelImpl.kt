package com.pdm.meetgroups.viewmodel.maps

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Journal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MapsViewModelImpl : ViewModel(), MapsViewModel {
    private val model = ModelImpl.modelRef

    override fun getUserNickname(): String? {
        return model.getUser()?.getState()?.nickname
    }

    override fun isAdmin(): Boolean {
        return model.isAdmin()!!
    }

    override fun updateUserLocation(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            model.updateUserLocation(location)
        }
    }

    override fun getNearJournalsAndLocations(location: Location): MutableLiveData<Hashtable<Location, Journal>?> {
        var hash : MutableLiveData<Hashtable<Location, Journal>?>
        viewModelScope.launch(Dispatchers.IO) {
            hash = model.getNearJournalsAndLocations(location)
        }
    }
}