package com.pdm.meetgroups.model

interface UserState {
    var bio: String
    var nickname: String
    var email: String
    var list: List<Journal>
    fun stateHandle()
}

data class ConcreteUser(
        override var bio: String = "",
        override var nickname: String = "",
        override var email: String = "",
        override var list: List<Journal> = emptyList()
    ): UserState {
    override fun stateHandle() {
        TODO("Not yet implemented, change state when journal is created")
    }
}

data class ConcreteAdmin(
        override var bio: String = "",
        override var nickname: String = "",
        override var email: String = "",
        override var list: List<Journal> = emptyList()
    ): UserState {
    override fun stateHandle() {
        TODO("Not yet implemented, change state when journal is closed")
    }
}

class UserContext {
    private val user: UserState
    private val admin: UserState

    private var state: UserState?

    init {
        user = ConcreteUser()
        admin = ConcreteAdmin()
        state = null // (AB): Should we set a default state? State will be initialized at app start i think
    }

    fun changeState(newState: UserState) {
        TODO("Not yet implemented")
    }
}