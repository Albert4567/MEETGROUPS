package com.pdm.meetgroups.model.entities

class UserContext {
    private val user : UserState
    private val admin : UserState

    private var state : UserState

    init {
        user = ConcreteUser()
        admin = ConcreteAdmin()
        state = user
    }

    fun changeState(newState : UserState) {
        state = state.stateHandle(newState)
    }

    fun getState () = state

    fun isAdmin () : Boolean {
        return (state as? ConcreteAdmin)?.isAdmin ?: false
    }
}