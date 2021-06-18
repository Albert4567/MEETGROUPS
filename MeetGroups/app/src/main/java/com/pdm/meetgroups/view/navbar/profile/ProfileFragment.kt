package com.pdm.meetgroups.view.navbar.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.pdm.meetgroups.viewmodel.ProfileViewModel
import com.pdm.meetgroups.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private val profileVMImpl: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}