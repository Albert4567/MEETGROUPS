package com.pdm.meetgroups.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.pdm.meetgroups.databinding.FragmentPostCreationBinding
import com.pdm.meetgroups.viewmodel.PostCreationViewModel

class PostCreationFragment : Fragment() {
    private val postCreationVM: PostCreationViewModel by viewModels()
    private lateinit var binding: FragmentPostCreationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPostCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun onRadioButtonClicked(view: View) {
        postCreationVM.setPostVisibility(view)
    }
}