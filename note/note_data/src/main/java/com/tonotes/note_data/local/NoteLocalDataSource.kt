package com.tonotes.note_data.local

import javax.inject.Inject

class NoteLocalDataSource @Inject constructor(
    private val noteDao: NoteDao
) {
    fun getNotes(searchQuery: String) = noteDao.getNotes(searchQuery)
}