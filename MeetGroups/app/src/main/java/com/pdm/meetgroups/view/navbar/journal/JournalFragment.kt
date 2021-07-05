package com.pdm.meetgroups.view.navbar.journal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.FragmentJournalBinding
import com.pdm.meetgroups.databinding.FragmentNotInJournalBinding
import com.pdm.meetgroups.view.EditJournalActivity
import com.pdm.meetgroups.view.adapter.ParticipantDrawerAdapter
import com.pdm.meetgroups.view.adapter.PostListAdapter
import com.pdm.meetgroups.viewmodel.journal.JournalDrawerViewModel
import com.pdm.meetgroups.viewmodel.journal.JournalViewModelImpl

class JournalFragment: Fragment() {
    private val journalVM: JournalViewModelImpl by viewModels()
    private val journalDrawerVM: JournalDrawerViewModel by viewModels()
    private lateinit var bindingInJournal: FragmentJournalBinding
    private lateinit var bindingNotInJournal: FragmentNotInJournalBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        journalVM.loadLocalUser()
        if (journalVM.isInJournal()) {
            bindingInJournal = FragmentJournalBinding.inflate(inflater, container, false)

            journalVM.getPosts().observe(viewLifecycleOwner, Observer {
                bindingInJournal.rvJournalPostlist.layoutManager = LinearLayoutManager(activity)
                bindingInJournal.rvJournalPostlist.adapter = PostListAdapter(journalVM, requireActivity())
            })

            journalVM.getTitle().observe(viewLifecycleOwner, Observer {
                bindingInJournal.tvJournalTitle.text = journalVM.getTitle().value
            })

            journalVM.getImage().observe(viewLifecycleOwner, Observer {
                bindingInJournal.imvJournalImage.setImageBitmap(journalVM.getImage().value)
            })

            journalDrawerVM.getParticipants().observe(viewLifecycleOwner, Observer {
                bindingInJournal.partecipantRecyclerView.layoutManager = LinearLayoutManager(activity)
                bindingInJournal.partecipantRecyclerView.adapter =
                    ParticipantDrawerAdapter(journalDrawerVM, requireActivity())
            })

            bindingInJournal.btnJournalEdit.setOnClickListener {
                journalVM.showEditJournalFragment(requireActivity())
            }

            bindingInJournal.btnJournalGroup.setOnClickListener {
                journalVM.showGroupParticipants(bindingInJournal)
            }

            bindingInJournal.btnJournalAddPost.setOnClickListener {
                journalVM.showPostCreationActivity(requireActivity())
            }

            return bindingInJournal.root
        }
        else {
            bindingNotInJournal = FragmentNotInJournalBinding.inflate(inflater, container, false)

            val html = getString(R.string.startHoliday)
            bindingNotInJournal.textHome.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)

            bindingNotInJournal.imageButtonJournal.setOnClickListener {
                val intent = Intent(activity, EditJournalActivity::class.java)
                startActivity(intent)
            }
            
            return bindingNotInJournal.root
        }
    }

    override fun onStart() {
        super.onStart()
        if(journalVM.isInJournal()) {
            journalVM.loadJournal()
            journalDrawerVM.setLocalJournal()
            journalDrawerVM.postLocalParticipantsValue()
        }
    }
}