package com.pdm.meetgroups.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.pdm.meetgroups.viewmodel.ProfileViewModel
import com.pdm.meetgroups.R
import com.pdm.meetgroups.viewmodel.PostCreationViewModelImpl

class ProfileFragment : Fragment() {
    private val profileVMImpl: ProfileViewModel by viewModels()
    private lateinit var binding: Fra

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}