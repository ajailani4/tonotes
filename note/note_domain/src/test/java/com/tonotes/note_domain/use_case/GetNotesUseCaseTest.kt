package com.tonotes.note_domain.use_case

import com.tonotes.core.Resource
import com.tonotes.note_data.local.entity.NoteEntity
import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.repository.NoteRepositoryFake
import com.tonotes.note_domain.util.ResourceType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseTest {
    private lateinit var noteRepositoryFake: NoteRepositoryFake
    private lateinit var getNotesUseCase: GetNotesUseCase

    @Before
    fun setUp() {
        noteRepositoryFake = NoteRepositoryFake()
        getNotesUseCase = GetNotesUseCase(noteRepositoryFake)
    }

    @Test
    fun `Get notes should return success`() = runBlocking {
        noteRepositoryFake.setResourceType(ResourceType.SUCCESS)

        val isSuccess = when (getNotesUseCase("").first()) {
            is Resource.Success -> true
            is Resource.Error -> false
        }

        assertEquals("Resource should be success", true, isSuccess)
    }

    @Test
    fun `Get notes should return fail`() = runBlocking {
        noteRepositoryFake.setResourceType(ResourceType.ERROR)

        val isSuccess = when (getNotesUseCase("").first()) {
            is Resource.Success -> true
            is Resource.Error -> false
        }

        assertEquals("Resource should be fail", false, isSuccess)
    }
}