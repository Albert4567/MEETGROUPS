package com.pdm.meetgroups.viewmodel.postcreation

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.RadioButton
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.*
import com.pdm.meetgroups.view.PostCreationActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostCreationViewModelImpl : ViewModel(), PostCreationViewModel {
    private val model = ModelImpl.modelRef
    private lateinit var title: String
    private lateinit var tags: ArrayList<String> // TODO(AB): Define default tags
    private lateinit var visibility: POST_STATUS
    private var images: MutableLiveData<ArrayList<Uri>> = MutableLiveData()

    fun getImages(): LiveData<ArrayList<Uri>> = images

    fun getImageBy(position: Int): Uri {
        var images = images.value!!
        return images[position]
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

    fun startFileChooser(activity: PostCreationActivity) {
        var intent = Intent()
        intent.setType("image/*")
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(activity, Intent.createChooser(intent, "Choose photo"), 111, null)
    }

    private fun pickMultipleImages(data: ClipData) {
        var imagesToDisplay = ArrayList<Uri>()
        val count = data.itemCount

        for (i in 0 until count) {
            val imageUri = data.getItemAt(i).uri
            imagesToDisplay.add(imageUri)
        }
        images.postValue(imagesToDisplay)
    }

    private fun pickSingleImage(data: Intent) {
        var imagesToDisplay = ArrayList<Uri>()

        data.data?.let { imagesToDisplay.add(it) }
        images.postValue(imagesToDisplay)
    }

    fun addChosenImages(data: Intent?) {
        if(data!!.clipData != null) {
            pickMultipleImages(data.clipData!!)
        } else {
            pickSingleImage(data)
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