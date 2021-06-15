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

    fun changeState() {
        state = state.stateHandle()
    }

    fun getState () = state

    fun isAdmin () : Boolean {
        return state is ConcreteAdmin
    }
}