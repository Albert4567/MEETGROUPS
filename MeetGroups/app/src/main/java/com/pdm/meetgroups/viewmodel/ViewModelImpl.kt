package com.pdm.meetgroups.viewmodel

import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.Post
import com.pdm.meetgroups.model.entities.UserContext
import com.pdm.meetgroups.observers.DBObserver

//TODO finire di implementare l'observer
class ViewModelImpl : ViewModel {
    private val dbObserver = DBObserverHandler(this)
    private val model = ModelImpl(dbObserver)
}