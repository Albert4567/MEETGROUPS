package com.pdm.meetgroups.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

enum class POST_STATUS(val value: String) {
    PUBLIC("public"),
    PRIVATE("private")
}

data class Post(
    var postID : String,
    var title: String,
    var description: String?,
    var postStatus: POST_STATUS,
    var creationDate: Timestamp,
    var creatorNickName: String,
    var spotLocation: GeoPoint,
    var tags: List<String>?,
    var images: List<String>?
)