package com.pdm.meetgroups.view.navbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdm.meetgroups.databinding.FragmentJournalBinding
import com.pdm.meetgroups.view.adapter.PostListAdapter
import com.pdm.meetgroups.viewmodel.JournalViewModel
import com.pdm.meetgroups.viewmodel.PostList


class JournalFragment : Fragment() {
    private lateinit var binding: FragmentJournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJournalBinding.inflate(inflater, container, false)
        val journalVM: JournalViewModel by viewModels()
        journalVM.getPosts().observe(viewLifecycleOwner, Observer {
            binding.rvJournalPostlist.layoutManager = LinearLayoutManager(activity)
            binding.rvJournalPostlist.adapter = PostListAdapter(journalVM)
        })
        return binding.root
    }
}