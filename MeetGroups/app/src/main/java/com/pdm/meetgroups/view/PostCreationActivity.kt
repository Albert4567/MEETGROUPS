package com.pdm.meetgroups.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.databinding.ActivityPostCreationBinding
import com.pdm.meetgroups.view.adapter.PostImageListAdapter
import com.pdm.meetgroups.viewmodel.postcreation.PostCreationViewModelImpl


class PostCreationActivity : AppCompatActivity() {
    private val postCreationVM: PostCreationViewModelImpl by viewModels()
    private lateinit var binding: ActivityPostCreationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostCreationBinding.inflate(layoutInflater)

        postCreationVM.getImages().observe(this, {
            val layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.rvPostCreationImages.layoutManager = layoutManager
            binding.rvPostCreationImages.adapter = PostImageListAdapter(postCreationVM)
        })

        binding.btnPostCreationUploadImage.setOnClickListener {
            postCreationVM.startFileChooser(this)
        }

        return setContentView(binding.root)
    }

    fun onRadioButtonClicked(view: View) {
        postCreationVM.setPostVisibility(view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 111 && resultCode == Activity.RESULT_OK ){
            postCreationVM.addChosenImages(data)
        }
    }
}
