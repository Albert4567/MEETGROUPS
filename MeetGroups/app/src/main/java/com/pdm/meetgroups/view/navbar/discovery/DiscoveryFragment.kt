package com.pdm.meetgroups.view.navbar.discovery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.FragmentDiscoveryBinding
import com.pdm.meetgroups.view.adapter.PostListAdapter
import com.pdm.meetgroups.viewmodel.discovery.DiscoveryViewModel
import kotlinx.android.synthetic.main.row_post.*

class  DiscoveryFragment : Fragment() {

    private lateinit var binding: FragmentDiscoveryBinding
    private val discoveryVM: DiscoveryViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoveryBinding.inflate(inflater, container, false)

        binding.discoverySearch.setOnClickListener {
            val tag = binding.discoverySearch.query.toString()
            discoveryVM.searchPostBy(tag)
        }

        discoveryVM.getBlankBitmap(requireContext())
        discoveryVM.loadPosts()

        discoveryVM.getPosts().observe(viewLifecycleOwner, Observer {
            binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            binding.searchRecyclerView.adapter = PostListAdapter(discoveryVM, requireActivity())
        })

        return binding.root
    }
}