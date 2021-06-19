package com.pdm.meetgroups.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.pdm.meetgroups.databinding.ActivityPostCreationBinding
import com.pdm.meetgroups.view.adapter.PostImageListAdapter
import com.pdm.meetgroups.view.navbar.MapsFragment
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

        binding.btnPostCreationPublish.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MapsFragment.LOCATION_REQUEST_CODE
                )
                return@setOnClickListener
            }
            postCreationVM.publishPost(this, Location(LocationManager.GPS_PROVIDER))
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
