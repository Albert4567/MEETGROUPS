package com.pdm.meetgroups.model.entities

import android.graphics.Bitmap
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import java.io.Serializable

enum class POST_STATUS(val value: String) : Serializable {
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
        var tags: MutableList<String>?,
)  : Serializable {
    var images: MutableList<Bitmap>? = null
}