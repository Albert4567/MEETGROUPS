package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.databinding.ActivityAddParticipantBinding
import com.pdm.meetgroups.view.adapter.AddParticipantListAdapter
import com.pdm.meetgroups.viewmodel.addparticipant.AddParticipantViewModelImpl

class AddParticipantActivity : AppCompatActivity() {
    private val addParticipantVM: AddParticipantViewModelImpl by viewModels()
    private lateinit var binding: ActivityAddParticipantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddParticipantBinding.inflate(layoutInflater)

        binding.svAddParticipantSearch.setOnClickListener {
            val name = binding.svAddParticipantSearch.query.toString()
            addParticipantVM.searchUserBy(name)
        }

        addParticipantVM.getUsers().observe(this, Observer {
            binding.rvAddParticipantList.layoutManager = LinearLayoutManager(this)
            binding.rvAddParticipantList.adapter = AddParticipantListAdapter(addParticipantVM, this)
        })

        return setContentView(binding.root)
    }
}