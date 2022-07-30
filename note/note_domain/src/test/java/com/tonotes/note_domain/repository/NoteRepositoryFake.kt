package com.tonotes.note_domain.repository

import com.tonotes.core.util.Resource
import com.tonotes.core.util.convertToDate
import com.tonotes.note_domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NoteRepositoryFake : NoteRepository {
    private val notes = mutableListOf(
        Note(
            id = 1,
            title = "Note 1",
            description = "This is a note",
            date = "2022-07-18 10:00:00".convertToDate()
        )
    )

    override fun getNotes(searchQuery: String) = flowOf(notes)

    override fun syncNotes(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getNoteDetail(id: Int) = flowOf(notes.find { it.id == id }!!)

    override suspend fun insertNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun editNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(id: Int) {
        TODO("Not yet implemented")
    }

    override fun uploadNotes(backupType: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun saveSelectedBackupType(backupType: Int) {
        TODO("Not yet implemented")
    }

    override fun getSelectedBackupType(): Flow<Int> {
        TODO("Not yet implemented")
    }
}