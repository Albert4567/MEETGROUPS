package com.pdm.meetgroups.viewmodel.postcreation

import android.content.ClipData
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.pdm.meetgroups.R
import com.pdm.meetgroups.model.ModelImpl
import com.pdm.meetgroups.model.entities.*
import com.pdm.meetgroups.view.PostCreationActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

typealias ImageList = ArrayList<Uri>

class PostCreationViewModelImpl : ViewModel(), PostCreationViewModel {
    private val model = ModelImpl.modelRef
    private lateinit var visibility: POST_STATUS
    private var images: MutableLiveData<ImageList> = MutableLiveData()

    fun getImages(): LiveData<ImageList> = images

    fun getImageBy(position: Int): Uri {
        val images = images.value!!
        return images[position]
    }

    override fun setPostVisibility(view: View) {
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

    override fun startFileChooser(activity: PostCreationActivity) {
        val intent = Intent()
        intent.setType("image/*")
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(activity, Intent.createChooser(intent, "Choose photo"), 111, null)
    }

    private fun pickMultipleImages(data: ClipData) {
        val imagesToDisplay = ImageList()
        val count = data.itemCount

        for (i in 0 until count) {
            val imageUri = data.getItemAt(i).uri
            imagesToDisplay.add(imageUri)
        }
        images.postValue(imagesToDisplay)
    }

    private fun pickSingleImage(data: Intent) {
        val imagesToDisplay = ImageList()

        data.data?.let { imagesToDisplay.add(it) }
        images.postValue(imagesToDisplay)
    }

    override fun addChosenImages(data: Intent?) {
        if(data!!.clipData != null) {
            pickMultipleImages(data.clipData!!)
        } else {
            pickSingleImage(data)
        }
    }

    private fun tagIsAllowed(tag: String): Boolean {
        return Tags.values().contains(
            Tags.valueOf(tag.toLowerCase(Locale.ROOT))
        )
    }

    private fun extractTagsFrom(tagsContainer: String): ArrayList<String> {
        val tags = ArrayList<String>()

        // TODO(AB): Check if this is working
        if(!tagsContainer.isEmpty()) {
            tagsContainer.trim().split("#").forEach {
                if (tagIsAllowed(it))
                    Log.println(Log.ASSERT, "AB", "entrato")
                tags.add(it)
            }
        }

        return tags
    }

    private fun createPost(title: String, description: String, tagsContainer: String, currentLocation: Location): Post {
        val creatorNickname = model.getUser()?.getState()?.nickname ?: "Unknown"
        val tags = extractTagsFrom(tagsContainer)
        val postImages = images.value as MutableList<Uri>?
        val spotLocation = GeoPoint(currentLocation.latitude, currentLocation.longitude)

        return Post(
            title+Timestamp.now(),
            title,
            description,
            visibility,
            Timestamp.now(),
            creatorNickname,
            spotLocation,
            tags,
        )
    }

    private fun insertionErrors(activity: PostCreationActivity): Boolean {
        val titleEditText = activity.findViewById<EditText>(R.id.et_post_creation_title)

        if (TextUtils.isEmpty(titleEditText.text.toString())) {
            titleEditText.error = "Insert title"
            return true
        }
        if(!::visibility.isInitialized) {
            return true
        }
        return false
    }

    override fun publishPost(activity: PostCreationActivity, currentLocation: Location?) {
        if(currentLocation == null || insertionErrors(activity)) {
            Toast.makeText(activity, "Oops! Something went wrong", Toast.LENGTH_SHORT).show()
            return
        }

        val post = createPost(
            activity.findViewById<EditText>(R.id.et_post_creation_title).text.toString(),
            activity.findViewById<EditText>(R.id.et_post_creation_description).text.toString(),
            activity.findViewById<EditText>(R.id.et_post_creation_tags).text.toString(),
            currentLocation
        )


        viewModelScope.launch(Dispatchers.IO) {
            //TODO TO CHECK
            val imageUris = ArrayList(images.value as MutableList<Uri>)
            val result = model.getJournal()?.let { model.createPost(it, post, imageUris) }
            withContext(Dispatchers.Main) {
                if(result != null && !result) {
                    Toast.makeText(activity, "Oops! Something went wrong", Toast.LENGTH_SHORT).show()
                } else {
                    activity.onBackPressed()
                }
            }
        }
    }
}