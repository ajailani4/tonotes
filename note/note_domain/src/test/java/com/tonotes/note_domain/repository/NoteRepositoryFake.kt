package com.tonotes.note_domain.repository

import com.tonotes.core.util.Resource
import com.tonotes.note_data.local.entity.NoteEntity
import com.tonotes.note_data.repository.NoteRepository
import com.tonotes.note_domain.util.ResourceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NoteRepositoryFake : NoteRepository {
    private val notes = mutableListOf(
        NoteEntity(
            id = 1,
            title = "Note 1",
            description = "This is a note",
            date = "2022-07-18"
        )
    )

    private lateinit var resourceType: ResourceType

    override fun getNotes(searchQuery: String): Flow<Resource<List<NoteEntity>>> =
        when (resourceType) {
            ResourceType.SUCCESS -> flowOf(Resource.Success(notes))

            ResourceType.ERROR -> flowOf(Resource.Error(null))
        }

    override fun getNoteDetail(id: Int): Flow<Resource<NoteEntity>> =
        when (resourceType) {
            ResourceType.SUCCESS -> flowOf(Resource.Success(notes.find { it.id == id }))

            ResourceType.ERROR -> flowOf(Resource.Error(null))
        }

    override suspend fun insertNote(noteEntity: NoteEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun editNote(noteEntity: NoteEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(id: Int) {
        TODO("Not yet implemented")
    }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}