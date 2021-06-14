package com.pdm.meetgroups.viewmodel

import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModel
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.entities.POST_STATUS

class PostCreationViewModel : ViewModel() {
    lateinit var title: String
    lateinit var tags: ArrayList<String> // TODO(AB): Define default tags
    lateinit var visibility: POST_STATUS
    lateinit var images: ArrayList<String>

    init {

    }

    fun setPostVisibility(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.radio_post_creation_private ->
                    if (checked)
                        visibility = POST_STATUS.PRIVATE
                R.id.radio_post_creation_public ->
                    if (checked)
                        visibility = POST_STATUS.PRIVATE
            }
        }
    }

    fun publishPost() {

    }
}