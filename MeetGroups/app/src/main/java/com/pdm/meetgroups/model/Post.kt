package com.pdm.meetgroups.model

import android.location.Location
import android.media.Image

enum class POST_STATUS(val value: String) {
    PUBLIC("public"),
    PRIVATE("private")
}

data class Post(
    var title: String,
    var description: String?,
    var postStatus: POST_STATUS,
    var creator: String,
    var spotLocation: Location,
    var tags: List<String>?, // TODO: Determine if this is the right type
    var images: List<Image>? // TODO: Determine if this is the right type
)