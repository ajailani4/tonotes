package com.tonotes.note_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tonotes.note_data.local.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
}