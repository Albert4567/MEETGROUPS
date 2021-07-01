package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.pdm.meetgroups.databinding.ActivityAddParticipantBinding
import com.pdm.meetgroups.databinding.ActivityEditJournalBinding
import com.pdm.meetgroups.viewmodel.addparticipant.AddParticipantViewModelImpl

class AddParticipantActivity : AppCompatActivity() {
    private val addParticipantVM: AddParticipantViewModelImpl by viewModels()
    private lateinit var binding: ActivityAddParticipantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddParticipantBinding.inflate(layoutInflater)

        binding.svAddParticipantSearch.setOnClickListener {

        }

        return setContentView(binding.root)
    }
}