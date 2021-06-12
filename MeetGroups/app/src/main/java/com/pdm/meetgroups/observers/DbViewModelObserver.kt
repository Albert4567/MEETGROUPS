package com.pdm.meetgroups.observers

import com.pdm.meetgroups.model.Post
import com.pdm.meetgroups.model.UserContext

interface DbViewModelObserver {
    fun notifyPostList (posts : List<Post>)

    fun notifyParticipants (participants : List<UserContext>)
}