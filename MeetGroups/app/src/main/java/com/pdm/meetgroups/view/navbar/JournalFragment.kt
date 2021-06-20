package com.pdm.meetgroups.view.navbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.databinding.FragmentJournalBinding
import com.pdm.meetgroups.view.adapter.PostListAdapter
import com.pdm.meetgroups.viewmodel.journal.JournalViewModelImpl

class JournalFragment: Fragment() {
    private val journalVM: JournalViewModelImpl by viewModels()
    private lateinit var binding: FragmentJournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentJournalBinding.inflate(inflater, container, false)

        journalVM.getPosts().observe(viewLifecycleOwner, Observer {
            binding.rvJournalPostlist.layoutManager = LinearLayoutManager(activity)
            binding.rvJournalPostlist.adapter = PostListAdapter(journalVM)
        })

        binding.btnJournalEdit.setOnClickListener {
            journalVM.showEditJournalFragment(requireActivity())
        }

        binding.btnJournalGroup.setOnClickListener {
            journalVM.showGroupPartecipants(it)
        }

        binding.btnJournalAddPost.setOnClickListener {
            journalVM.showPostCreationFragment(requireActivity())
        }

        return binding.root
    }
}