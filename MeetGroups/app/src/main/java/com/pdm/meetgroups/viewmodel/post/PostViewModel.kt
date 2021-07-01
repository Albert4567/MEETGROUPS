package com.pdm.meetgroups.viewmodel.post

import com.pdm.meetgroups.databinding.ActivityPostBinding
import com.pdm.meetgroups.view.PostActivity

interface PostViewModel {
    fun getPostBy(position: Int)

    fun getTagBy(position: Int): String?

    fun tagCount(): Int

    fun setTitle(binding: ActivityPostBinding)

    fun setDescription(binding: ActivityPostBinding)

    fun deletePost(activity: PostActivity)
}