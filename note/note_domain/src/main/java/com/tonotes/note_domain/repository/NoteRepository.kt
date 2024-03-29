package com.tonotes.note_domain.repository

import com.tonotes.core.util.Resource
import com.tonotes.note_domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(searchQuery: String): Flow<List<Note>>

    fun syncNotes(): Flow<Resource<String>>

    fun getNoteDetail(id: Int): Flow<Note?>

    suspend fun insertNote(note: Note)

    suspend fun editNote(note: Note)

    suspend fun deleteNote(id: Int)

    fun uploadNotes(backupType: Int)

    suspend fun saveSelectedBackupType(backupType: Int)

    fun getSelectedBackupType(): Flow<Int>
}