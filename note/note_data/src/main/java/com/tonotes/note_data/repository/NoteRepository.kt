package com.tonotes.note_data.repository

import com.tonotes.core.Resource
import com.tonotes.note_data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<Resource<List<NoteEntity>>>
}