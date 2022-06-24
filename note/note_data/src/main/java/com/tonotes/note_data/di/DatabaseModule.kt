package com.tonotes.note_data.di

import android.content.Context
import androidx.room.Room
import com.tonotes.note_data.local.NoteDao
import com.tonotes.note_data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(db: NoteDatabase): NoteDao = db.noteDao
}