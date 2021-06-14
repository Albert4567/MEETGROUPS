package com.pdm.meetgroups.model.entities

data class ConcreteUser(
        override var bio: String = "",
        override var nickname: String = "",
        override var email: String = "",
        override var list: List<Journal> = emptyList(),
        override var visibilityOnMap: Boolean = false,
        override var openJournalID: String? = null
    ): UserState {

    override fun stateHandle() {
        TODO("Not yet implemented, change state when journal is created")
    }
}