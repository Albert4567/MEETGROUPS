package com.pdm.meetgroups.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.databinding.ActivityEditJournalBinding
import com.pdm.meetgroups.view.adapter.ParticipantListAdapter
import com.pdm.meetgroups.viewmodel.editjournal.EditJournalViewModelImpl

class EditJournalActivity : AppCompatActivity() {
    private val editJournalVM: EditJournalViewModelImpl by viewModels()
    private lateinit var binding: ActivityEditJournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditJournalBinding.inflate(layoutInflater)

        editJournalVM.loadLocalUser()

        binding.etEditJournalTitle.setText(editJournalVM.getJournalTitle())

        binding.imvEditJournalJournalphoto.setImageBitmap(editJournalVM.getJournalImage())

        binding.imvEditJournalJournalphoto.setOnClickListener {
            editJournalVM.startFileChooser(this)
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 111 && resultCode == Activity.RESULT_OK ){
            editJournalVM.updateJournalImage(data, this)
        }
    }
}