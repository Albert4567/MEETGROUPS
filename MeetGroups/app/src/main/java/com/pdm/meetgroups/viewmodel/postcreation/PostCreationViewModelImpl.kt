package com.pdm.meetgroups.viewmodel.postcreation

import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostCreationViewModelImpl : ViewModel(), PostCreationViewModel {
    private val model = ModelImpl.modelRef

    lateinit var title: String
    lateinit var tags: ArrayList<String> // TODO(AB): Define default tags
    lateinit var visibility: POST_STATUS
    lateinit var images: ArrayList<String>

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

    override fun createPost(): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            //model.createPost()
        }
        return true
    }
}