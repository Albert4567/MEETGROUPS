package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.databinding.ActivityEditJournalBinding
import com.pdm.meetgroups.view.adapter.ParticipantListAdapter
import com.pdm.meetgroups.viewmodel.editjournal.EditJournalViewModelImpl

// TODO(AB): Set new journal image from gallery
class EditJournalActivity : AppCompatActivity() {
    private val editJournalVM: EditJournalViewModelImpl by viewModels()
    private lateinit var binding: ActivityEditJournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditJournalBinding.inflate(layoutInflater)

        binding.etEditJournalTitle.setText(editJournalVM.getJournalTitle())

        binding.btnEditJournalConfirmTitleChange.setOnClickListener {
            editJournalVM.updateJournalTitle(this)
        }

        editJournalVM.getParticipants().observe(this, Observer {
            binding.rvEditJournalParticipants.layoutManager = LinearLayoutManager(this)
            binding.rvEditJournalParticipants.adapter = ParticipantListAdapter(editJournalVM, this)
        })

        binding.btnEditJournalAddParticipant.setOnClickListener {
            editJournalVM.showAddParticipantActivity(this)
        }

        binding.btnEditJournalClose.setOnClickListener {
            editJournalVM.closeJournal(this)
        }

        return setContentView(binding.root)
    }
}