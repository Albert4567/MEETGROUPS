package com.pdm.meetgroups.model.entities

interface UserState {
    var bio : String
    var nickname : String
    var email : String
    var list : List<Journal>
    var visibilityOnMap : Boolean
    var openJournalID : String?

    fun stateHandle() : UserState
}