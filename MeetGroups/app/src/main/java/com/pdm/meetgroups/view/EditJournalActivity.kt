package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.databinding.ActivityEditJournalBinding
import com.pdm.meetgroups.view.adapter.ParticipantListAdapter
import com.pdm.meetgroups.viewmodel.EditJournalViewModel

class EditJournalActivity : AppCompatActivity() {
    private val editJournalVM: EditJournalViewModel by viewModels()
    private lateinit var binding: ActivityEditJournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditJournalBinding.inflate(layoutInflater)

        editJournalVM.getParticipants().observe(this, Observer {
            binding.rvEditJournalParticipants.layoutManager = LinearLayoutManager(this)
            binding.rvEditJournalParticipants.adapter = ParticipantListAdapter(editJournalVM)
        })

        return setContentView(binding.root)
    }
}