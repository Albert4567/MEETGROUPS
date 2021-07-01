package com.pdm.meetgroups.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.meetgroups.databinding.ActivityPostBinding
import com.pdm.meetgroups.view.adapter.PostImageListAdapter
import com.pdm.meetgroups.view.adapter.TagListAdapter
import com.pdm.meetgroups.viewmodel.post.PostViewModelImpl

class PostActivity : AppCompatActivity() {
    private val postVM: PostViewModelImpl by viewModels()
    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)

        postVM.getPostBy(intent.getIntExtra("position", 0))
        postVM.setTitle(binding)
        postVM.setDescription(binding)

        var layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvPostTags.layoutManager = layoutManager
        binding.rvPostTags.adapter = TagListAdapter(postVM)

        var layoutManagers = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvPostImages.layoutManager = layoutManagers
        binding.rvPostImages.adapter = PostImageListAdapter(postVM)

        binding.btnPostDelete.setOnClickListener {
            postVM.deletePost(this)
        }

        return setContentView(binding.root)
    }
}