package com.tonotes.note_data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tonotes.note_data.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
}