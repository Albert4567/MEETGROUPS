package com.pdm.meetgroups.view.navbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.databinding.FragmentJournalBinding
import com.pdm.meetgroups.view.adapter.PostListAdapter
import com.pdm.meetgroups.viewmodel.ViewModelImpl
import com.pdm.meetgroups.viewmodel.journal.JournalViewModel


class JournalFragment : Fragment() {
    private val viewModel = ViewModelImpl()
    private val journalVMImpl: JournalViewModel = viewModel.getJournalViewModel()
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

        journalVMImpl.getPosts().observe(viewLifecycleOwner, Observer {
            binding.rvJournalPostlist.layoutManager = LinearLayoutManager(activity)
            binding.rvJournalPostlist.adapter = PostListAdapter(journalVMImpl)
        })

        binding.btnJournalEdit.setOnClickListener {
            journalVMImpl.showEditJournalFragment(requireActivity())
        }

        binding.btnJournalGroup.setOnClickListener {
            journalVMImpl.showGroupPartecipants(it)
        }

        binding.btnJournalAddPost.setOnClickListener {
            journalVMImpl.showPostCreationFragment(requireActivity())
        }

        return binding.root
    }
}