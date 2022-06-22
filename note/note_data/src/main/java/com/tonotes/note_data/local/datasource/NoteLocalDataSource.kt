package com.tonotes.note_data.local.datasource

import com.tonotes.note_data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteLocalDataSource {
    fun getNotes(): Flow<List<NoteEntity>>
}