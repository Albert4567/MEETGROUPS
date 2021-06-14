package com.pdm.meetgroups.viewmodel

import com.pdm.meetgroups.model.entities.Journal

interface JournalViewModel {
    fun loadJournalPosts (journal: Journal)

    fun loadParticipants (journal : Journal)
}