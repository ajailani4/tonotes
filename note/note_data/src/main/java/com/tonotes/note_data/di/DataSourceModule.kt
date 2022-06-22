package com.tonotes.note_data.di

import com.tonotes.note_data.local.datasource.NoteLocalDataSource
import com.tonotes.note_data.local.datasource.NoteLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindNoteLocalDataSource(
        noteLocalDataSourceImpl: NoteLocalDataSourceImpl
    ): NoteLocalDataSource
}