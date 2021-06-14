package com.pdm.meetgroups.model.entities

class UserContext {
    private val user : UserState
    private val admin : UserState

    private var state : UserState?

    init {
        user = ConcreteUser()
        admin = ConcreteAdmin()
        state = null // TODO(AB): Should we set a default state? State will be initialized at app start i think
    }

    fun changeState(newState: UserState) {
        state!!.stateHandle()
        state = newState
    }

    fun getState () = state

    fun isAdmin () : Boolean {
        return state is ConcreteAdmin
    }
}