package com.pdm.meetgroups.model.entities

import com.google.firebase.firestore.GeoPoint

data class ConcreteAdmin(
        override var bio: String = "",
        override var nickname: String = "",
        override var email: String = "",
        override var list: List<Journal> = emptyList(),
        override var visibilityOnMap: Boolean = false,
        override var openJournalID: String? = null,
        var currentPosition: GeoPoint? = null
): UserState {
    override fun stateHandle() {
        TODO("Not yet implemented, change state when journal is closed")
    }
}