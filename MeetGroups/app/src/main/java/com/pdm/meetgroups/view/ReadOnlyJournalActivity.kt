package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.ActivityReadOnlyJournalBinding
import com.pdm.meetgroups.view.adapter.PostListAdapter
import com.pdm.meetgroups.viewmodel.journal.ReadOnlyJournalViewModelImpl

class ReadOnlyJournalActivity : AppCompatActivity() {
    private val readOnlyJournalVM: ReadOnlyJournalViewModelImpl by viewModels()
    private lateinit var binding: ActivityReadOnlyJournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_only_journal)

        binding = ActivityReadOnlyJournalBinding.inflate(layoutInflater)

        readOnlyJournalVM.getPosts().observe(this, Observer {
            binding.rvJournalPostlist.layoutManager = LinearLayoutManager(this)
            binding.rvJournalPostlist.adapter = PostListAdapter(readOnlyJournalVM)
        })
        
        return setContentView(binding.root)
    }
}