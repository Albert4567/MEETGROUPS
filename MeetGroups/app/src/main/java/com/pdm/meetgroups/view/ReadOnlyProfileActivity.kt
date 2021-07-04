package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.FragmentProfileBinding
import com.pdm.meetgroups.view.adapter.JournalListAdapter
import com.pdm.meetgroups.viewmodel.profile.ProfileViewModelImpl
import com.pdm.meetgroups.viewmodel.profile.ReadOnlyProfileViewModelImpl
import kotlinx.android.synthetic.main.fragment_profile.*

class ReadOnlyProfileActivity : AppCompatActivity() {
    private val profileVMImpl: ReadOnlyProfileViewModelImpl by viewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        binding = FragmentProfileBinding.inflate(layoutInflater)

        profileVMImpl.loadUser(intent.getStringExtra("user")!!)

        binding.settingsButton.visibility = View.INVISIBLE

        profileVMImpl.getJournals().observe(this, Observer {
            binding.journalView.layoutManager = LinearLayoutManager(this)
            binding.journalView.adapter = JournalListAdapter(profileVMImpl, this)
        })

        profileVMImpl.getImage().observe(this, Observer {
            binding.ProfileImageView.setImageBitmap(profileVMImpl.getImage().value)
        })

        profileVMImpl.getBio().observe(this, Observer {
            binding.bioTextView.text = if (profileVMImpl.getBio().value != "")
                    profileVMImpl.getBio().value
                else binding.bioTextView.text
        })

        profileVMImpl.getNickname().observe(this, Observer {
            binding.nameTextView.text = profileVMImpl.getNickname().value
        })

        return setContentView(binding.root)
    }
}