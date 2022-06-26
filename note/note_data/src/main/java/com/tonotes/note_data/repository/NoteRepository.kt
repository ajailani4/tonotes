package com.tonotes.note_data.repository

import com.tonotes.core.Resource
import com.tonotes.note_data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(searchQuery: String): Flow<Resource<List<NoteEntity>>>

    fun getNoteDetail(id: Int): Flow<Resource<NoteEntity>>

    suspend fun insertNote(noteEntity: NoteEntity)

    suspend fun editNote(noteEntity: NoteEntity)
}