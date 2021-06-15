package com.pdm.meetgroups.model.entities

import com.google.firebase.firestore.GeoPoint

data class ConcreteAdmin(
        override var bio: String = "",
        override var nickname: String = "",
        override var email: String = "",
        override var list: List<Journal> = emptyList(),
        override var openJournalID: String? = null,
        override var visibilityOnMap: Boolean = false,
        var currentPosition: GeoPoint? = null
): UserState {
    override fun stateHandle(newState : UserState) : UserState {
        return ConcreteUser(
            newState.bio,
            newState.nickname,
            newState.email,
            newState.list,
            newState.openJournalID,
            newState.visibilityOnMap)
    }
}