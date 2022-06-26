package com.tonotes.note_data.local

import com.tonotes.note_data.local.entity.NoteEntity
import javax.inject.Inject

class NoteLocalDataSource @Inject constructor(
    private val noteDao: NoteDao
) {
    fun getNotes(searchQuery: String) = noteDao.getNotes(searchQuery)

    fun getNoteDetail(id: Int) = noteDao.getNoteDetail(id)

    suspend fun insertNote(noteEntity: NoteEntity) = noteDao.insertNote(noteEntity)

    suspend fun editNote(noteEntity: NoteEntity) = noteDao.editNote(noteEntity)
}