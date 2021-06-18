package com.pdm.meetgroups.view.navbar.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.databinding.FragmentProfileBinding
import com.pdm.meetgroups.view.EditProfileActivity
import com.pdm.meetgroups.view.adapter.JournalListAdapter
import com.pdm.meetgroups.viewmodel.profile.ProfileViewModelImpl


class ProfileFragment : Fragment() {
    private val profileVMImpl: ProfileViewModelImpl by viewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.settingsButton.setOnClickListener {
            onSettingsButtonClick()
        }

        profileVMImpl.getJournals().observe(viewLifecycleOwner, Observer {
            binding.journalView.layoutManager = LinearLayoutManager(activity)
            binding.journalView.adapter = JournalListAdapter(profileVMImpl)
        })

        binding.LinkImageView.setImageBitmap(profileVMImpl.getUserImage())

        binding.bioTextView.text = profileVMImpl.getUserBio()

        binding.nameTextView.text = profileVMImpl.getUserNickname()

        return binding.root
    }

    private fun onSettingsButtonClick() {
        val intent = Intent(activity, EditProfileActivity::class.java)
        startActivity(intent)
    }
}