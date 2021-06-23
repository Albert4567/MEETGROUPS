package com.pdm.meetgroups.view.navbar.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pdm.meetgroups.databinding.FragmentDiscoveryBinding

class  DiscoveryFragment : Fragment() {

    private lateinit var binding: FragmentDiscoveryBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoveryBinding.inflate(inflater, container, false)

        return binding.root
    }
}