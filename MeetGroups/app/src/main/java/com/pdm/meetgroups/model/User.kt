package com.pdm.meetgroups.model

interface UserState {
    var bio: String
    var nickname: String
    var email: String
    var list: List<Journal>
    fun stateHandle()
}

data class ConcreteUser(
        override var bio: String,
        override var nickname: String,
        override var email: String,
        override var list: List<Journal>
        ): UserState {
    override fun stateHandle() {
        TODO("Not yet implemented, change state when journal is created")
    }
}

data class ConcreteAdmin(
        override var bio: String,
        override var nickname: String,
        override var email: String,
        override var list: List<Journal>
    ): UserState {
    override fun stateHandle() {
        TODO("Not yet implemented, change state when journal is closed")
    }
}

class UserContext(var state: UserState) {

    fun changeState() {
        TODO("Not yet implemented")
    }
}