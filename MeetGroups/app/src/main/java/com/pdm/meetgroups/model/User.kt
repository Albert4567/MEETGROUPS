package com.pdm.meetgroups.model

import com.google.firebase.firestore.GeoPoint

data class ConcreteUser(
        override var bio: String = "",
        override var nickname: String = "",
        override var email: String = "",
        override var list: List<Journal> = emptyList(),
        override var visibilityOnMap: Boolean = false,
    ): UserState {
    override fun stateHandle() {
        TODO("Not yet implemented, change state when journal is created")
    }
}

data class ConcreteAdmin(
        override var bio: String = "",
        override var nickname: String = "",
        override var email: String = "",
        override var list: List<Journal> = emptyList(),
        override var visibilityOnMap: Boolean = false,
        var currentPosition: GeoPoint? = null
    ): UserState {
    override fun stateHandle() {
        TODO("Not yet implemented, change state when journal is closed")
    }
}

class UserContext {
    private val user : UserState
    private val admin : UserState

    private var state : UserState?

    init {
        user = ConcreteUser()
        admin = ConcreteAdmin()
        state = null // (AB): Should we set a default state? State will be initialized at app start i think
    }

    fun changeState(newState: UserState) {
        //state!!.stateHandle()
        state = newState
    }

    fun getState () = state
}