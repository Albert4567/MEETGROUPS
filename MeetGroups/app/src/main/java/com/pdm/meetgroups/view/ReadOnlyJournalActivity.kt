package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.ActivityReadOnlyJournalBinding
import com.pdm.meetgroups.view.adapter.ParticipantDrawerAdapter
import com.pdm.meetgroups.view.adapter.PostListAdapter
import com.pdm.meetgroups.viewmodel.journal.JournalDrawerViewModel
import com.pdm.meetgroups.viewmodel.journal.ReadOnlyJournalViewModelImpl

class ReadOnlyJournalActivity : AppCompatActivity() {
    private val readOnlyJournalVM: ReadOnlyJournalViewModelImpl by viewModels()
    private val journalDrawerVM: JournalDrawerViewModel by viewModels()
    private lateinit var binding: ActivityReadOnlyJournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_only_journal)

        binding = ActivityReadOnlyJournalBinding.inflate(layoutInflater)

        readOnlyJournalVM.loadJournal(intent.getStringExtra("journal")!!)
        readOnlyJournalVM.getPosts().observe(this, Observer {
            binding.rvJournalPostlist.layoutManager = LinearLayoutManager(this)
            binding.rvJournalPostlist.adapter = PostListAdapter(readOnlyJournalVM, this)
        })

        readOnlyJournalVM.getTitle().observe(this, Observer { title ->
            binding.tvJournalTitle.text = title
        })

        readOnlyJournalVM.getImage().observe(this, Observer { image ->
            binding.imvJournalImage.setImageBitmap(image)
        })

        journalDrawerVM.getParticipants().observe(this, Observer {
            binding.partecipantRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.partecipantRecyclerView.adapter = ParticipantDrawerAdapter(journalDrawerVM, this)
        })

        binding.btnJournalGroup.setOnClickListener {
            journalDrawerVM.setJournal(readOnlyJournalVM.getJournal())
            readOnlyJournalVM.showGroupParticipants(binding)
        }

        return setContentView(binding.root)
    }
}