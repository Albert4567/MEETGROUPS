package com.pdm.meetgroups.model.entities

data class ConcreteUser(
        override var bio: String = "",
        override var nickname: String = "",
        override var email: String = "",
        override var list: List<Journal> = emptyList(),
        override var openJournalID: String? = null,
        override var visibilityOnMap: Boolean = false
    ): UserState {

    override fun stateHandle() : UserState {
        return ConcreteAdmin(bio, nickname, email, list, openJournalID, visibilityOnMap)
    }
}