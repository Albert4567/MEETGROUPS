package com.pdm.meetgroups.model

enum class JOURNAL_STATUS {
    IN_PROGRESS,
    CLOSED
}

data class Journal(
    var title: String,
    var posts: List<Post>,
    var status: JOURNAL_STATUS,
    var users: List<UserContext>
)
