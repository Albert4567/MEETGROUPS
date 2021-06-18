package com.pdm.meetgroups.model.entities

import android.graphics.Bitmap

interface UserState {
    var bio : String
    var nickname : String
    var email : String
    var list : List<Journal>
    var visibilityOnMap : Boolean
    var openJournalID : String?
    var userImage : Bitmap?

    fun stateHandle(newState : UserState) : UserState
}