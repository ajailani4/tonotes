package com.tonotes.note_data.local.datasource

import com.tonotes.note_data.local.NoteDao
import javax.inject.Inject

class NoteLocalDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteLocalDataSource {
    override fun getNotes() = noteDao.getNotes()
}