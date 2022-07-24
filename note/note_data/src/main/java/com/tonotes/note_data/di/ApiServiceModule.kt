package com.tonotes.note_data.di

import com.tonotes.note_data.remote.NoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    @Singleton
    fun provideNoteService(retrofit: Retrofit): NoteService =
        retrofit.create(NoteService::class.java)
}