package com.pdm.meetgroups.viewmodel.postcreation

import android.content.Intent
import android.location.Location
import android.view.View
import com.pdm.meetgroups.view.PostCreationActivity

interface PostCreationViewModel {
    fun startFileChooser(activity: PostCreationActivity)

    fun addChosenImages(data: Intent?)

    fun setPostVisibility(view: View)

    fun publishPost(activity: PostCreationActivity, currentLocation: Location?)
}