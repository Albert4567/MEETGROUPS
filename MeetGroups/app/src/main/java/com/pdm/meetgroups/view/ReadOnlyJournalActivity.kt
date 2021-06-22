package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.ActivityPostCreationBinding
import com.pdm.meetgroups.databinding.ActivityReadOnlyJournalBinding
import com.pdm.meetgroups.view.adapter.PostImageListAdapter

class ReadOnlyJournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadOnlyJournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_only_journal)

        binding = ActivityReadOnlyJournalBinding.inflate(layoutInflater)
        
        return setContentView(binding.root)
    }
}