package com.pdm.meetgroups.viewmodel.maps

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.pdm.meetgroups.model.entities.Journal
import java.util.*

interface MapsViewModel {
    fun getUserNickname () : String?

    fun isAdmin() : Boolean

    fun updateUserLocation (location : Location)

    suspend fun getNearJournalsAndLocations (location: Location) : Hashtable<Location, Journal>?
}