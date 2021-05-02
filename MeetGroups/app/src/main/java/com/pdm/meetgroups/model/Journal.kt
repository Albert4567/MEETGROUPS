package com.pdm.meetgroups.model

enum class JOURNAL_STATUS(val value: String) {
    IN_PROGRESS("inProgress"),
    CLOSED("closed")
}

data class Journal(
    var journalID : String,
    var title : String,
    var posts : List<Post>,
    var status : JOURNAL_STATUS,
    var users : List<UserContext>
)
