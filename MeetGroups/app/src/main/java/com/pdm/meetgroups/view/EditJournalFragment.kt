package com.pdm.meetgroups.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.databinding.FragmentEditJournalBinding
import com.pdm.meetgroups.view.adapter.ParticipantListAdapter
import com.pdm.meetgroups.view.adapter.PostListAdapter
import com.pdm.meetgroups.viewmodel.EditJournalViewModel

class EditJournalFragment: Fragment() {
    private val editJournalVM: EditJournalViewModel by viewModels()
    private lateinit var binding: FragmentEditJournalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditJournalBinding.inflate(inflater, container, false)

        editJournalVM.getParticipants().observe(viewLifecycleOwner, Observer {
            binding.rvEditJournalParticipants.layoutManager = LinearLayoutManager(activity)
            binding.rvEditJournalParticipants.adapter = ParticipantListAdapter(editJournalVM)
        })

        return binding.root
    }
}