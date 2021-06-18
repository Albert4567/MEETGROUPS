package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.pdm.meetgroups.databinding.ActivityPostCreationBinding
import com.pdm.meetgroups.viewmodel.postcreation.PostCreationViewModelImpl

class PostCreationActivity : AppCompatActivity() {
    private val postCreationVMImpl: PostCreationViewModelImpl by viewModels()
    private lateinit var binding: ActivityPostCreationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostCreationBinding.inflate(layoutInflater)

        return setContentView(binding.root)
    }

    fun onRadioButtonClicked(view: View) {
        postCreationVMImpl.setPostVisibility(view)
    }
}