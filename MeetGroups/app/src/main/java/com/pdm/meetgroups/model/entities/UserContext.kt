package com.pdm.meetgroups.model.entities

class UserContext {
    private var state : UserState

    init {
        state = ConcreteUser()
    }

    fun changeState(newState : UserState) {
        state = state.stateHandle(newState)
    }

    fun setInitialState (newState: UserState) {
        state = newState
    }

    fun getState () = state

    fun isAdmin () : Boolean {
        return state is ConcreteAdmin
    }
}