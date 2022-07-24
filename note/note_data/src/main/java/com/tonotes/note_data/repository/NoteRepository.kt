package com.tonotes.note_data.repository

import com.tonotes.core.util.Resource
import com.tonotes.note_data.local.entity.NoteEntity
import com.tonotes.note_data.remote.dto.NotesRequest
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(searchQuery: String): Flow<Resource<List<NoteEntity>>>

    fun getNoteDetail(id: Int): Flow<Resource<NoteEntity>>

    suspend fun insertNote(noteEntity: NoteEntity)

    suspend fun editNote(noteEntity: NoteEntity)

    suspend fun deleteNote(id: Int)

    fun uploadNotes(notesRequest: NotesRequest): Flow<Resource<Any>>
}