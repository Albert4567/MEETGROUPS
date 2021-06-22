package com.pdm.meetgroups.viewmodel.readonlyjournal
import android.view.View
import androidx.lifecycle.LiveData
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.model.entities.Post

typealias PostList = ArrayList<Post>


interface ReadOnlyJournalViewModel {
    fun getPosts () : LiveData<PostList>

    fun showGroupParticipants(view: View)

    fun loadJournalPosts(loadJournal: Journal)
}