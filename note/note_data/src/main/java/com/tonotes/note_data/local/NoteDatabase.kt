package com.tonotes.note_data.local

import androidx.room.Database
import com.tonotes.note_data.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase {
}