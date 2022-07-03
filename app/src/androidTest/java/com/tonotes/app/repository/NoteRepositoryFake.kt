package com.tonotes.app.repository

import com.tonotes.core.Resource
import com.tonotes.note_data.local.entity.NoteEntity
import com.tonotes.note_data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NoteRepositoryFake : NoteRepository {
    private val notes = mutableListOf(
        NoteEntity(
            id = 1,
            title = "Note 1",
            description = "This is a note",
            date = "30 Jun 2022"
        )
    )

    override fun getNotes(searchQuery: String): Flow<Resource<List<NoteEntity>>> =
        flowOf(Resource.Success(notes))

    override fun getNoteDetail(id: Int): Flow<Resource<NoteEntity>> =
        flowOf(Resource.Success(notes.find { it.id == id }))

    override suspend fun insertNote(noteEntity: NoteEntity) {
        notes.add(noteEntity)
    }

    override suspend fun editNote(noteEntity: NoteEntity) {
        val index = notes.indexOf(notes.find { it.id == noteEntity.id })
        notes[index] = noteEntity
    }

    override suspend fun deleteNote(id: Int) {
        notes.removeIf { it.id == id }
    }
}